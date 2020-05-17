package com.tugos.dst.admin.controller;


import com.tugos.dst.admin.service.SettingService;
import com.tugos.dst.admin.utils.ResultVoUtil;
import com.tugos.dst.admin.vo.GameConfigVO;
import com.tugos.dst.admin.vo.ResultVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/setting")
public class SettingController {

    @Autowired
    private SettingService settingService;


    @GetMapping("/index")
    @RequiresPermissions("setting:index")
    public String index(Model model) throws Exception{
        String token = settingService.getToken();
        model.addAttribute("token",token);
        return "/setting/index";
    }


    @PostMapping("/saveConfig")
    @RequiresPermissions("setting:saveConfig")
    @ResponseBody
    public ResultVo saveConfig(GameConfigVO vo) throws Exception{
        settingService.saveConfig(vo);
        return ResultVoUtil.success();
    }




}
