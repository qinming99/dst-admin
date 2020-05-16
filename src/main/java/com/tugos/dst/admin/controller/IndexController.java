package com.tugos.dst.admin.controller;


import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {


    /**
     * 首页
     */
    @GetMapping("/index")
    @RequiresPermissions("index")
    public String index(Model model) {
        return "/index";
    }




}
