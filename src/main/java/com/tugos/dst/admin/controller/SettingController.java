package com.tugos.dst.admin.controller;

import com.tugos.dst.admin.entity.Server;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/setting")
public class SettingController {


    @GetMapping("/index")
    @RequiresPermissions("setting:index")
    public String index(Model model) throws Exception{

        return "/setting/index";
    }

}
