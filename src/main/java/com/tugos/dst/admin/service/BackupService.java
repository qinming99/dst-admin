package com.tugos.dst.admin.service;


import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.ZipUtil;
import com.tugos.dst.admin.common.ResultCodeEnum;
import com.tugos.dst.admin.common.ResultVO;
import com.tugos.dst.admin.config.I18nResourcesConfig;
import com.tugos.dst.admin.exception.ResultException;
import com.tugos.dst.admin.utils.DstConstant;
import com.tugos.dst.admin.utils.FileUtils;
import com.tugos.dst.admin.vo.BackupFileVO;
import com.tugos.dst.admin.vo.GameSnapshotVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author qinming
 * @date 2020-05-17
 * <p> 存档管理服务，游戏的存档保存在 ~/.klei/DoNotStarveTogether 目录 </p>
 */
@Service
@Slf4j
public class BackupService {

    /**
     * 总存档大小最大2G
     */
    @Value("${dcs.max.backup.size:2048}")
    private Integer maxBackupSize;
    private ShellService shellService;

    /**
     * 获取备份文件信息 包括zip，tar 压缩包
     *
     * @return 信息列表
     */
    public List<BackupFileVO> getBackupFileInfo() {
        List<BackupFileVO> result = new ArrayList<>();
        String backupPath = DstConstant.ROOT_PATH + "/" + DstConstant.DST_DOC_PATH;
        List<String> allFileList = FileUtils.getFiles(backupPath);
        //过滤出所有备份文件压缩包 包括zip，tar
        List<String> backupFileList = new ArrayList<>();
        allFileList.forEach(e -> {
            String extName = FileUtil.extName(e);
            if (StringUtils.equalsAnyIgnoreCase(DstConstant.BACKUP_FILE_EXTENSION_NON_POINT_ZIP, extName)
                    || StringUtils.equalsAnyIgnoreCase(DstConstant.BACKUP_FILE_EXTENSION_NON_POINT, extName)) {
                backupFileList.add(e);
            }
        });
        if (CollectionUtils.isNotEmpty(backupFileList)) {
            SimpleDateFormat sdf = new SimpleDateFormat(DatePattern.NORM_DATETIME_PATTERN);
            for (String e : backupFileList) {
                BackupFileVO vo = new BackupFileVO();
                File file = new File(e);
                String name = file.getName();
                long lastModified = file.lastModified();
                String time = sdf.format(lastModified);
                vo.setCreateTime(time);
                vo.setFileSize(file.length());
                vo.setFileName(name);
                vo.setTime(DateUtil.parse(vo.getCreateTime(), DatePattern.NORM_DATETIME_FORMAT));
                result.add(vo);
            }
        }
        if (CollectionUtils.isNotEmpty(result)) {
            //排序，降序
            result.sort((o1, o2) -> DateUtil.compare(o1.getTime(), o2.getTime()));
            Collections.reverse(result);
        }
        return result;
    }

    /**
     * 删除备份
     *
     * @param fileName 文件名字
     */
    public boolean deleteBackup(String fileName) {
        if (StringUtils.isNotBlank(fileName)) {
            String basePath = DstConstant.ROOT_PATH + DstConstant.SINGLE_SLASH + DstConstant.DST_DOC_PATH;
            String filePath = basePath + "/" + fileName;
            File file = new File(filePath);
            if (file.exists()) {
                //删除
                return file.delete();
            }
        }
        return false;
    }

    /**
     * 恢复存档 需要暂停游戏，清空之前的记录
     *
     * @param name 备份的文件名称全称
     */
    public ResultVO<String> restore(String name) {
        if (!this.checkBackupIsExists(name)) {
            //未安装dst
            return ResultVO.fail(I18nResourcesConfig.getMessage("tip.home.backup.error2") + name);
        }
        //清空在恢复
        delMyDediServer();
        String extName = FileUtil.extName(name);
        if (StringUtils.equalsAnyIgnoreCase(DstConstant.BACKUP_FILE_EXTENSION_NON_POINT_ZIP, extName)) {
            //zip
            revertZIPBackup(name);
        } else {
            //tar
            delMyDediServer();
            shellService.revertBackup(name);
        }
        return ResultVO.success();
    }

    /**
     * 删除MyDediServer目录
     */
    public void delMyDediServer() {
        String path = DstConstant.ROOT_PATH + DstConstant.SINGLE_SLASH + DstConstant.DST_USER_GAME_CONFG_PATH;
        log.warn("删除MyDediServer目录:{}", path);
        FileUtil.del(path);
    }

    /**
     * 校验存档文件是否存在
     *
     * @param name 文件名称 全称
     * @return true 存在
     */
    private boolean checkBackupIsExists(String name) {
        boolean flag = false;
        if (StringUtils.isNotBlank(name)) {
            String path = DstConstant.ROOT_PATH + DstConstant.SINGLE_SLASH + DstConstant.DST_DOC_PATH;
            File file = new File(path);
            flag = file.exists();
        }
        return flag;
    }

    /**
     * 重命名
     *
     * @param fileName    老名字
     * @param newFileName 新名字
     * @return true 成功
     */
    public ResultVO<String> rename(String fileName, String newFileName) {
        if (StringUtils.isNoneBlank(fileName, newFileName)) {
            newFileName = fileNameFilter(newFileName);
            String basePath = DstConstant.ROOT_PATH + DstConstant.SINGLE_SLASH + DstConstant.DST_DOC_PATH;
            String filePath = basePath + DstConstant.SINGLE_SLASH + fileName;
            String suffix = "." + FileUtil.extName(filePath);
            String newFilePath = basePath + DstConstant.SINGLE_SLASH + newFileName + suffix;
            File newFile = new File(newFilePath);
            if (newFile.exists()) {
                return ResultVO.fail("相同名称文件已经存在");
            }
            File file = new File(filePath);
            if (file.exists()) {
                //重命名
                boolean renameTo = file.renameTo(newFile);
                if (renameTo) {
                    return ResultVO.success();
                } else {
                    return ResultVO.fail("重命名失败");
                }
            }
        }
        return ResultVO.fail("重命名失败");
    }

    /**
     * 创建备份 zip格式的
     *
     * @param name 文件名称
     * @return 结果
     */
    public ResultVO<String> backup(String name) {
        if (!this.checkGameFileIsExists()) {
            //未安装dst
            return ResultVO.fail(I18nResourcesConfig.getMessage("tip.home.backup.error"));
        }
        List<BackupFileVO> backupList = this.getBackupFileInfo();
        if (CollectionUtils.isNotEmpty(backupList)) {
            long sum = backupList.stream().mapToLong(BackupFileVO::getFileSize).sum();
            double total = sum / 1024.0D / 1024;
            if (total > maxBackupSize) {
                throw new ResultException(ResultCodeEnum.BACKUP_MAX_SIZE_ERROR);
            }
        }
        String fileName;
        if (StringUtils.isNotBlank(name)) {
            fileName = name + ".zip";
        } else {
            String serverName = "未知房间";
            String playDate = "未知天数";
            String season = "未知季节";
            String filePath = DstConstant.ROOT_PATH + DstConstant.DST_USER_GAME_CONFG_PATH + DstConstant.SINGLE_SLASH + DstConstant.DST_USER_CLUSTER_INI_NAME;
            File file = new File(filePath);
            if (file.exists()) {
                List<String> list = FileUtil.readLines(filePath, StandardCharsets.UTF_8);
                if (CollectionUtils.isNotEmpty(list)) {
                    for (String s : list) {
                        if (s.contains("cluster_name")) {
                            String[] split = s.split("=");
                            if (StringUtils.isNotBlank(split[1])) {
                                serverName = split[1].trim();
                            }
                        }
                    }
                }
            }
            BackupService backupService = new BackupService();
            GameSnapshotVO gameSnapshot = backupService.getGameSnapshot();
            if (gameSnapshot != null) {
                playDate = gameSnapshot.getPlayDay() + "天";
                season = gameSnapshot.getSeasonChinese();
            }
            fileName = String.format("%s_%s_%s_%s.zip", DateUtil.format(new Date(), "yyyyMMddHHmmss"), serverName, playDate, season);
        }
        createBackup(fileName);
        return ResultVO.success();
    }

    /**
     * 校验游戏存档文件是否存在
     *
     * @return true 存在
     */
    private boolean checkGameFileIsExists() {
        String path = DstConstant.ROOT_PATH + DstConstant.SINGLE_SLASH + DstConstant.DST_USER_GAME_CONFG_PATH;
        File file = new File(path);
        return file.exists();
    }

    /**
     * 备份游戏存档
     */
    public void createBackup(String fileName) {
        fileName = fileNameFilter(fileName);
        String basePath = DstConstant.ROOT_PATH + "/.klei/DoNotStarveTogether/";
        String scrPath = basePath + "MyDediServer";
        ZipUtil.zip(scrPath, basePath + fileName, true);
    }


    public String fileNameFilter(String fileName){
        fileName = StringUtils.deleteWhitespace(fileName);
        fileName = RegExUtils.replaceAll(fileName, "[/\\\\:*?|]", "");
        fileName = RegExUtils.replaceAll(fileName, "[\"<>]", "");
        fileName = RegExUtils.replaceAll(fileName, "[^\\u0000-\\uFFFF]", "");
        return fileName;
    }
    /**
     * 获取快照信息
     */
    public GameSnapshotVO getGameSnapshot() {
        GameSnapshotVO gameSnapshotVO = null;
        try {
            String snapshot = getSnapshot();
            log.info("快照信息：{}", snapshot);
            if (StringUtils.isNotBlank(snapshot)) {
                gameSnapshotVO = parseSnapshot(snapshot);
            }
        } catch (Exception e) {
            log.warn("解析快照失败：", e);
        }
        return gameSnapshotVO;
    }

    /**
     * 获取存档中的快照元信息
     */
    private static String getSnapshot() {
        String filePath = DstConstant.ROOT_PATH + DstConstant.DST_USER_GAME_CONFG_PATH + DstConstant.SINGLE_SLASH + DstConstant.DST_USER_GAME_MASTER_SESSION;
        File sessionFile = new File(filePath);
        List<File> targetFileList = new ArrayList<>();
        if (sessionFile.exists()) {
            File[] files = sessionFile.listFiles();
            if (files != null && files.length > 0) {
                for (File targetFile : files) {
                    if (!targetFile.isHidden() && targetFile.isDirectory()) {
                        targetFileList.add(targetFile);
                    }
                }
            }
        }
        List<String> result = new ArrayList<>();
        File startFile;
        if (CollectionUtils.isNotEmpty(targetFileList)) {
            startFile = targetFileList.get(0);
            for (File file : targetFileList) {
                if (FileUtil.size(startFile) < FileUtil.size(file)) {
                    startFile = file;
                }
            }
        } else {
            return null;
        }
        File[] snapshotFiles = startFile.listFiles();
        if (snapshotFiles != null && snapshotFiles.length > 0) {
            for (File snapshotFile : snapshotFiles) {
                String extName = FileUtil.extName(snapshotFile);
                if ("meta".equalsIgnoreCase(extName)) {
                    result.add(snapshotFile.getAbsolutePath());
                }
            }
        }
        result.sort(BackupService::compare);
        Collections.reverse(result);
        if (result.size() > 0) {
            String targetPath = result.get(0);
            return FileUtil.readString(targetPath, StandardCharsets.UTF_8);
        }
        return null;
    }


    /**
     * 解析快照信息
     *
     * @param content 快照内容
     * @return 快照信息
     */
    private static GameSnapshotVO parseSnapshot(String content) {
        GameSnapshotVO gameSnapshotVO = new GameSnapshotVO();
        gameSnapshotVO.setOriginal(content);
        content = content.replace("\"", "");
        content = content.replace("[", "");
        content = content.replace("]", "");
        gameSnapshotVO.setPlayDay("未知");
        gameSnapshotVO.setSeason("未知");
        ArrayList<String> cyclesList = ReUtil.findAll("cycles=[1-9]\\d*", content, 0, new ArrayList<>());
        ArrayList<String> seasonList = ReUtil.findAll("season=\\w*", content, 0, new ArrayList<>());
        if (CollectionUtils.isNotEmpty(cyclesList)) {
            //cycles=313
            String[] split = cyclesList.get(0).split("=");
            if (split.length > 0) {
                gameSnapshotVO.setPlayDay(split[1]);
            }
        }
        if (CollectionUtils.isNotEmpty(seasonList)) {
            //season="winter"
            String[] split = seasonList.get(0).split("=");
            if (split.length > 0) {
                gameSnapshotVO.setSeason(split[1].replace("\"", ""));
            }
        }
        return gameSnapshotVO;
    }


    public static int compare(String path1, String path2) {
        if (path1 == null) {
            return -1;
        } else if (path2 == null) {
            return 1;
        } else if (path1.equals(path2)) {
            return 0;
        }
        File file1 = new File(path1);
        File file2 = new File(path2);
        long lastModified1 = file1.lastModified();
        long lastModified2 = file2.lastModified();
        return (int) (lastModified1 - lastModified2);
    }

    /**
     * 恢复游戏存档
     *
     * @param fileName 备份的游戏名称
     */
    public void revertZIPBackup(String fileName) {
        String basePath = DstConstant.ROOT_PATH + "/.klei/DoNotStarveTogether/";
        String outFileDir = basePath + "MyDediServer";
        FileUtil.del(outFileDir);
        FileUtil.mkdir(outFileDir);
        String zipPath = basePath + fileName;
        String tmpPath = basePath + "tmp";
        File file = null;
        boolean unzipFail = false;
        try {
            file = ZipUtil.unzip(zipPath, tmpPath);
        } catch (Exception e) {
            unzipFail = true;
            log.error("解压存档文件失败,默认模式：" + fileName, e);
        }
        if (unzipFail) {
            try {
                file = ZipUtil.unzip(zipPath, tmpPath, CharsetUtil.CHARSET_GBK);
            } catch (Exception e) {
                log.error("解压存档文件失败,GBK模式：" + fileName, e);
                throw new ResultException(ResultCodeEnum.BACKUP_UNZIP_ERROR);
            }
        }
        String resultPath = FileUtils.findMasterPath(file);
        log.info("解压时找到Master目录：" + resultPath);
        if (StringUtils.isBlank(resultPath)) {
            log.warn("存档文件有问题：" + fileName);
            throw new ResultException(ResultCodeEnum.BACKUP_CONTENT_ERROR);
        }
        try {
            Set<String> list = new HashSet<>();
            list.add("Master");
            list.add("Caves");
            list.add("cluster.ini");
            list.add("cluster_token.txt");
            list.add("blacklist.txt");
            list.add("adminlist.txt");
            for (String target : list) {
                String scrPath = resultPath + "/" + target;
                File tmpFile = new File(scrPath);
                if (tmpFile.exists()) {
                    FileUtil.copy(scrPath, outFileDir, true);
                }
            }
        } catch (Exception e) {
            log.error("解压存档文件时复制存档失败：" + fileName);
            throw new ResultException(ResultCodeEnum.BACKUP_COPY_ERROR);
        } finally {
            //清理临时释放目录
            FileUtil.del(tmpPath);
        }
    }

    /**
     * 下载文件
     *
     * @param fileName 文件名称
     * @param response 响应
     * @throws Exception 异常
     */
    public void download(String fileName, HttpServletResponse response) throws Exception {
        String extName = FileUtil.extName(fileName);
        if (StringUtils.isNotBlank(fileName) && !fileName.contains(DstConstant.BACKUP_ERROR_PATH) && StringUtils.equalsAnyIgnoreCase(extName,
                DstConstant.BACKUP_FILE_EXTENSION_NON_POINT,DstConstant.BACKUP_FILE_EXTENSION_NON_POINT_ZIP)) {
            String filepath = DstConstant.ROOT_PATH + "/" + DstConstant.DST_DOC_PATH;
            filepath += "/" + fileName;
            File file = new File(filepath);
            if (file.exists()) {
                //文件存在
                response.setHeader("content-type", "application/octet-stream");
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
                byte[] buffer = new byte[1024];
                try (FileInputStream fis = new FileInputStream(file);
                     BufferedInputStream bis = new BufferedInputStream(fis);
                     OutputStream os = response.getOutputStream()) {
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    os.flush();
                } catch (Exception e) {
                    log.error("下载文件失败：", e);
                }
                return;
            }
        }
        //返回空文件
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("文件不存在.txt", "UTF-8"));
        response.getOutputStream().flush();
    }

    /**
     * 上传存档
     */
    public ResultVO<String> upload(MultipartFile file) throws Exception {
        String filepath = DstConstant.ROOT_PATH + "/" + DstConstant.DST_DOC_PATH + "/" + file.getOriginalFilename();
        File dest = new File(filepath);
        if (!dest.exists()) {
            file.transferTo(dest);
        } else {
            return ResultVO.fail(I18nResourcesConfig.getMessage("tip.backup.file.exist"));
        }
        return ResultVO.success();
    }

    @Autowired
    public void setShellService(ShellService shellService) {
        this.shellService = shellService;
    }
}
