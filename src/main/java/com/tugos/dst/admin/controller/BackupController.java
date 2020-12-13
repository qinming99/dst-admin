package com.tugos.dst.admin.controller;


import com.tugos.dst.admin.common.ResultVO;
import com.tugos.dst.admin.service.BackupService;
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
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        if (!"tar".equalsIgnoreCase(suffix)) {
            return ResultVO.fail("请上传tar文件");
        }
        return backupService.upload(file);
    }

    @GetMapping("/deleteBackup")
    @ResponseBody
    @RequiresAuthentication
    public ResultVO<String> deleteBackup(String fileName) {
        log.info("删除备份:{}", fileName);
        backupService.deleteBackup(fileName);
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
