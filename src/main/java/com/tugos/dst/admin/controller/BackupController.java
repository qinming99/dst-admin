package com.tugos.dst.admin.controller;


import com.tugos.dst.admin.common.ResultVO;
import com.tugos.dst.admin.service.BackupService;
import com.tugos.dst.admin.vo.BackupFileVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/backup")
@Slf4j
public class BackupController {

    private BackupService backupService;


    @GetMapping("/index")
    @RequiresPermissions("backup:index")
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

    @GetMapping("/deleteBackup")
    @ResponseBody
    @RequiresAuthentication
    public ResultVO deleteBackup(String fileName) {
        log.info("删除备份:{}", fileName);
        backupService.deleteBackup(fileName);
        return ResultVO.success();
    }

    @GetMapping("/rename")
    @ResponseBody
    @RequiresAuthentication
    public ResultVO rename(String fileName, String newFileName) {
        log.info("重命名备份:{},新文件名:{}", fileName, newFileName);
        backupService.rename(fileName, newFileName);
        return ResultVO.success();
    }

    @Autowired
    public void setBackupService(BackupService backupService) {
        this.backupService = backupService;
    }
}
