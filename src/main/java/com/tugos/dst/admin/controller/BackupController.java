package com.tugos.dst.admin.controller;


import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONUtil;
import com.tugos.dst.admin.common.ResultVO;
import com.tugos.dst.admin.config.I18nResourcesConfig;
import com.tugos.dst.admin.service.BackupService;
import com.tugos.dst.admin.utils.DstConstant;
import com.tugos.dst.admin.vo.BackupFileVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author qinming
 * @date 2020-05-17
 * <p> 存档管理，游戏的存档保存在 ~/.klei/DoNotStarveTogether 目录 </p>
 */
@Controller
@RequestMapping("/backup")
@Slf4j
public class BackupController {

    private BackupService backupService;


    @GetMapping("/index")
    @RequiresAuthentication
    public String index() {
        return "/backup/index";
    }

    @GetMapping("/getBackupList")
    @ResponseBody
    @RequiresAuthentication
    public ResultVO<List<BackupFileVO>> getBackupList() {
        return ResultVO.data(backupService.getBackupFileInfo());
    }

    @GetMapping(value = "/download")
    @RequiresAuthentication
    public void download(String fileName, HttpServletResponse response) throws Exception {
        log.info("下载文件：" + fileName);
        backupService.download(fileName, response);
    }

    @PostMapping(value = "/upload")
    @RequiresAuthentication
    @ResponseBody
    public ResultVO<String> upload(@RequestParam("file") MultipartFile file) throws Exception {
        String suffix = FileUtil.extName(file.getOriginalFilename());
        if (!DstConstant.BACKUP_FILE_EXTENSION_NON_POINT.equalsIgnoreCase(suffix)) {
            return ResultVO.fail(I18nResourcesConfig.getMessage("tip.backup.tarfile"));
        }
        return backupService.upload(file);
    }

    @PostMapping("/deleteBackup")
    @ResponseBody
    @RequiresAuthentication
    public ResultVO<String> deleteBackup(@RequestBody String[] fileNames) {
        log.info("删除备份:{}", JSONUtil.toJsonStr(fileNames));
        if (fileNames != null && fileNames.length > 0) {
            for (String s : fileNames) {
                backupService.deleteBackup(s);
            }
        }
        return ResultVO.success();
    }

    @GetMapping("/rename")
    @ResponseBody
    @RequiresAuthentication
    public ResultVO<String> rename(String fileName, String newFileName) {
        log.info("重命名备份:{},新文件名:{}", fileName, newFileName);
        backupService.rename(fileName, newFileName);
        return ResultVO.success();
    }

    @Autowired
    public void setBackupService(BackupService backupService) {
        this.backupService = backupService;
    }
}
