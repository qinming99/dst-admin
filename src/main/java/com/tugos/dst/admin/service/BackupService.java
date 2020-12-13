package com.tugos.dst.admin.service;


import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.tugos.dst.admin.common.ResultVO;
import com.tugos.dst.admin.utils.DstConstant;
import com.tugos.dst.admin.utils.FileUtils;
import com.tugos.dst.admin.vo.BackupFileVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author qinming
 * @date 2020-05-17
 * <p> 存档管理服务，游戏的存档保存在 ~/.klei/DoNotStarveTogether 目录 </p>
 */
@Service
@Slf4j
public class BackupService {

    /**
     * 获取备份文件信息
     *
     * @return 信息列表
     */
    public List<BackupFileVO> getBackupFileInfo() {
        List<BackupFileVO> result = new ArrayList<>();
        String backupPath = DstConstant.ROOT_PATH + "/" + DstConstant.DST_DOC_PATH;
        List<String> allFileList = FileUtils.getFiles(backupPath);
        //过滤出所有备份文件压缩包
        List<String> backupFileList = allFileList.stream()
                .filter(e -> e.contains(DstConstant.BACKUP_FILE_EXTENSION)).collect(Collectors.toList());
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
     * 重命名
     *
     * @param fileName    老名字
     * @param newFileName 新名字
     * @return true 成功
     */
    public boolean rename(String fileName, String newFileName) {
        if (StringUtils.isNoneBlank(fileName, newFileName)) {
            String basePath = DstConstant.ROOT_PATH + DstConstant.SINGLE_SLASH + DstConstant.DST_DOC_PATH;
            String filePath = basePath + DstConstant.SINGLE_SLASH + fileName;
            String newFilePath = basePath + DstConstant.SINGLE_SLASH + newFileName + DstConstant.BACKUP_FILE_EXTENSION;
            File file = new File(filePath);
            if (file.exists()) {
                //删除
                return file.renameTo(new File(newFilePath));
            }
        }
        return false;
    }

    /**
     * 下载文件
     *
     * @param fileName 文件名称
     * @param response 响应
     * @throws Exception 异常
     */
    public void download(String fileName, HttpServletResponse response) throws Exception {
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
        } else {
            //返回空文件
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("文件不存在.txt", "UTF-8"));
            response.getOutputStream().flush();
        }
    }

    /**
     * 上传存档
     */
    public ResultVO<String> upload(MultipartFile file) throws Exception {
        String filepath = DstConstant.ROOT_PATH + "/" + DstConstant.DST_DOC_PATH + "/" + file.getOriginalFilename();
        File dest = new File(filepath);
        if (!dest.exists()) {
            file.transferTo(dest);
        }else {
            return ResultVO.fail("文件已经存在了");
        }
        return ResultVO.success();
    }
}
