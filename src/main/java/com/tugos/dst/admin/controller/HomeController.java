package com.tugos.dst.admin.controller;


import com.tugos.dst.admin.common.ResultVO;
import com.tugos.dst.admin.service.HomeService;
import com.tugos.dst.admin.service.ShellService;
import com.tugos.dst.admin.vo.DstServerInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;

/**
 * @author qinming
 * @date 2020-05-17
 * <p> 游戏管理器 </p>
 */
@Controller
@Slf4j
@RequestMapping("/home")
public class HomeController {

    private HomeService homeService;

    private ShellService shellService;


    /**
     * 首页
     */
    @GetMapping("/index")
    @RequiresAuthentication
    public String index(HttpServletRequest request) {
        Locale locale = LocaleContextHolder.getLocale();
        if (Locale.CHINA.getLanguage().equals(locale.getLanguage())) {
            request.setAttribute("lang", "zh");
        } else {
            request.setAttribute("lang", "en");
        }
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
        log.debug("获取服务器的信息");
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
        return homeService.stop(type);
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

    @GetMapping("/sendBroadcast")
    @RequiresAuthentication
    @ResponseBody
    public ResultVO<String> sendBroadcast(@RequestParam String message){
        log.info("发送公告："+message);
        shellService.sendBroadcast(message);
        return ResultVO.success();
    }

    @GetMapping("/getPlayerList")
    @RequiresAuthentication
    @ResponseBody
    public ResultVO<List<String>> getPlayerList() throws Exception{
        List<String> playerList = shellService.getPlayerList();
        return ResultVO.data(playerList);
    }

    @GetMapping("/kickPlayer")
    @RequiresAuthentication
    @ResponseBody
    public ResultVO<String> kickPlayer(@RequestParam String userId){
        log.info("踢出玩家："+userId);
        shellService.kickPlayer(userId);
        return ResultVO.success();
    }

    @GetMapping("/rollback")
    @RequiresAuthentication
    @ResponseBody
    public ResultVO<String> rollback(@RequestParam Integer dayNum){
        log.info("回滚指定的天数："+dayNum);
        shellService.rollback(dayNum);
        return ResultVO.success();
    }

    @GetMapping("/regenerate")
    @RequiresAuthentication
    @ResponseBody
    public ResultVO<String> regenerate(){
        log.info("重置世界...");
        shellService.regenerate();
        return ResultVO.success();
    }


    @Autowired
    public void setHomeService(HomeService homeService) {
        this.homeService = homeService;
    }

    @Autowired
    public void setShellService(ShellService shellService) {
        this.shellService = shellService;
    }
}
