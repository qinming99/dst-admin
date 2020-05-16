package com.tugos.dst.admin.controller;


import com.tugos.dst.admin.entity.Server;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/monitor")
public class MonitorController {



    @GetMapping("/index")
    @RequiresPermissions("monitor:index")
    public String index(Model model) throws Exception{
        Server server = new Server();
        server.copyTo();
        model.addAttribute(server);
        return "/monitor/index";
    }

}
