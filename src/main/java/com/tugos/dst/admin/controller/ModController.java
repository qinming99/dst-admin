package com.tugos.dst.admin.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mod")
public class ModController {



    @GetMapping("/index")
    @RequiresPermissions("mod:index")
    public String index(Model model) throws Exception{
        return "/mod/index";
    }


}
