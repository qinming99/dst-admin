package com.tugos.dst.admin.controller;


import com.tugos.dst.admin.common.ResultVO;
import com.tugos.dst.admin.service.HomeService;
import com.tugos.dst.admin.vo.DstServerInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
@RequestMapping("/home")
public class HomeController {

    private HomeService homeService;


    /**
     * 首页
     */
    @GetMapping("/index")
    @RequiresAuthentication
    public String index() {
        log.info("访问首页");
        return "/home/index";
    }


    /**
     * 获取服务器的信息
     * 包括饥荒状态和硬件信息
     */
    @GetMapping("/getSystemInfo")
    @ResponseBody
    @RequiresAuthentication
    public ResultVO<DstServerInfoVO> getSystemInfo() throws Exception {
        log.info("获取服务器的信息");
        return ResultVO.data(homeService.getSystemInfo());
    }

    @GetMapping("/start")
    @RequiresAuthentication
    @ResponseBody
    public ResultVO<String> start(@RequestParam Integer type) throws Exception {
        log.info("启动服务器，type={}", type);
        ResultVO<String> resultVO = homeService.start(type);
        Thread.sleep(2000);
        return resultVO;
    }


    @GetMapping("/stop")
    @RequiresAuthentication
    @ResponseBody
    public ResultVO<String> stop(@RequestParam Integer type) throws Exception {
        log.info("停止服务器，type={}", type);
        ResultVO<String> resultVO = homeService.stop(type);
        Thread.sleep(2000);
        return resultVO;
    }

    @GetMapping("/updateGame")
    @RequiresAuthentication
    @ResponseBody
    public ResultVO<String> updateGame() {
        log.info("更新游戏");
        return homeService.updateGame();
    }


    @GetMapping("/backup")
    @RequiresAuthentication
    @ResponseBody
    public ResultVO<String> backup(@RequestParam(required = false) String name) throws Exception {
        log.info("备份游戏,{}", name);
        ResultVO<String> resultVO = homeService.backup(name);
        Thread.sleep(2000);
        return resultVO;
    }

    @GetMapping("/restore")
    @RequiresAuthentication
    @ResponseBody
    public ResultVO<String> restore(@RequestParam String name) throws Exception {
        log.info("恢复存档,{}", name);
        ResultVO<String> resultVO = homeService.restore(name);
        Thread.sleep(2000);
        return resultVO;
    }

    @GetMapping("/delRecord")
    @RequiresAuthentication
    @ResponseBody
    public ResultVO<String> delRecord() throws Exception{
        log.info("清理游戏记录");
        homeService.delRecord();
        Thread.sleep(2000);
        return ResultVO.success();
    }

    @Autowired
    public void setHomeService(HomeService homeService) {
        this.homeService = homeService;
    }
}
