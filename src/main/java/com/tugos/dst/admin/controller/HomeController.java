package com.tugos.dst.admin.controller;


import com.tugos.dst.admin.service.HomeService;
import com.tugos.dst.admin.utils.ResultVoUtil;
import com.tugos.dst.admin.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class HomeController {

    @Autowired
    private HomeService homeService;


    /**
     * 首页
     */
    @GetMapping("/index")
    @RequiresPermissions("home")
    public String index(Model model) {
        log.info("访问首页");
        model.addAttribute("masterStatus",homeService.getMasterStatus());
        model.addAttribute("cavesStatus",homeService.getCavesStatus());
        return "home";
    }

    @GetMapping("/start")
    @RequiresPermissions("home:start")
    @ResponseBody
    public ResultVo start(@RequestParam(required = false) Integer type){
        log.info("启动服务器，type={}",type);
        homeService.start(type);
        ResultVo success = ResultVoUtil.success();
        success.setData(type);
        return success;
    }


    @GetMapping("/stop")
    @RequiresPermissions("home:stop")
    @ResponseBody
    public ResultVo stop(@RequestParam(required = false) Integer type){
        log.info("停止服务器，type={}",type);
        homeService.stop(type);
        ResultVo success = ResultVoUtil.success();
        success.setData(type);
        return success;
    }




}
