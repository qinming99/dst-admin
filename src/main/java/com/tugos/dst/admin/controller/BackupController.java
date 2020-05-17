package com.tugos.dst.admin.controller;


import com.tugos.dst.admin.entity.Server;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/backup")
@Slf4j
public class BackupController {


    @GetMapping("/index")
    @RequiresPermissions("backup:index")
    public String index(Model model) throws Exception{
        Server server = new Server();
        server.copyTo();
        model.addAttribute(server);
        return "/backup/index";
    }


}
