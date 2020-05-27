package com.tugos.dst.admin.controller;


import com.tugos.dst.admin.service.BackupService;
import com.tugos.dst.admin.utils.ResultVoUtil;
import com.tugos.dst.admin.vo.BackupFileVO;
import com.tugos.dst.admin.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/backup")
@Slf4j
public class BackupController {

    @Autowired
    private BackupService backupService;


    @GetMapping("/index")
    @RequiresPermissions("backup:index")
    public String index(Model model) throws Exception{
        List<BackupFileVO> list = backupService.getBackupFileInfo();
        model.addAttribute("fileList",list);
        return "/backup/index";
    }

    @GetMapping("/del")
    @RequiresPermissions("mod:del")
    @ResponseBody
    public ResultVo del(String fileName) throws Exception {
        log.info("删除{}备份",fileName);
        boolean del = backupService.del(fileName);
        System.out.println(del);
        return ResultVoUtil.success();
    }


}
