package com.tugos.dst.admin.controller;


import com.alibaba.fastjson.JSON;
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
        model.addAttribute("masterStatus", homeService.getMasterStatus());
        model.addAttribute("cavesStatus", homeService.getCavesStatus());
        model.addAttribute("backupList", homeService.showBackup());
        model.addAttribute("backupListJson", JSON.toJSONString(homeService.showBackup()));
        return "home";
    }

    @GetMapping("/start")
    @RequiresPermissions("home:start")
    @ResponseBody
    public ResultVo start(@RequestParam(required = false) Integer type) {
        log.info("启动服务器，type={}", type);
        homeService.start(type);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ResultVo success = ResultVoUtil.success();
        success.setData(type);
        return success;
    }


    @GetMapping("/stop")
    @RequiresPermissions("home:stop")
    @ResponseBody
    public ResultVo stop(@RequestParam(required = false) Integer type) {
        log.info("停止服务器，type={}", type);
        homeService.stop(type);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ResultVo success = ResultVoUtil.success();
        success.setData(type);
        return success;
    }

    @GetMapping("/updateGame")
    @RequiresPermissions("home:updateGame")
    @ResponseBody
    public ResultVo updateGame() {
        log.info("更新游戏");
        homeService.updateGame();
        return ResultVoUtil.success();
    }


    @GetMapping("/backup")
    @RequiresPermissions("home:backup")
    @ResponseBody
    public ResultVo backup(@RequestParam(required = false) String name) {
        log.info("备份游戏,{}", name);
        homeService.backup(name);
        return ResultVoUtil.success();
    }

    @GetMapping("/restore")
    @RequiresPermissions("home:restore")
    @ResponseBody
    public ResultVo restore(@RequestParam(required = false) String name) {
        log.info("恢复存档,{}", name);
        homeService.restore(name);
        return ResultVoUtil.success();
    }

    @GetMapping("/delRecord")
    @RequiresPermissions("home:delRecord")
    @ResponseBody
    public ResultVo delRecord() {
        log.info("清理游戏记录,{}");
        homeService.delRecord();
        return ResultVoUtil.success();
    }


}
