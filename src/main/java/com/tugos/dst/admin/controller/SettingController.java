package com.tugos.dst.admin.controller;


import com.alibaba.fastjson.JSON;
import com.tugos.dst.admin.service.SettingService;
import com.tugos.dst.admin.utils.ResultVoUtil;
import com.tugos.dst.admin.vo.GameConfigVO;
import com.tugos.dst.admin.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class SettingController {

    @Autowired
    private SettingService settingService;


    @GetMapping("/index")
    @RequiresPermissions("setting:index")
    public String index(Model model) throws Exception{
        GameConfigVO config = settingService.getConfig();
        model.addAttribute("config",config);
        return "/setting/index";
    }


    @PostMapping("/saveConfig")
    @RequiresPermissions("setting:saveConfig")
    @ResponseBody
    public ResultVo saveConfig(GameConfigVO vo) throws Exception{
        log.info("保存游戏配置，{}", JSON.toJSONString(vo));
        settingService.saveConfig(vo);
        return ResultVoUtil.success();
    }




}
