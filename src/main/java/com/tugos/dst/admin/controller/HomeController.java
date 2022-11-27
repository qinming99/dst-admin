package com.tugos.dst.admin.controller;


import com.tugos.dst.admin.common.ResultVO;
import com.tugos.dst.admin.service.BackupService;
import com.tugos.dst.admin.service.HomeService;
import com.tugos.dst.admin.service.ShellService;
import com.tugos.dst.admin.vo.DstServerInfoVO;
import com.tugos.dst.admin.vo.GameArchiveVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
    private BackupService backupService;


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
        return homeService.start(type);
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
        return backupService.backup(name);
    }

    @GetMapping("/restore")
    @RequiresAuthentication
    @ResponseBody
    public ResultVO<String> restore(@RequestParam String name) throws Exception {
        log.info("恢复存档,{}", name);
        homeService.stopServer();
        return backupService.restore(name);
    }

    @GetMapping("/delRecord")
    @RequiresAuthentication
    @ResponseBody
    public ResultVO<String> delRecord() throws Exception {
        log.info("清理游戏记录");
        homeService.delRecord();
        Thread.sleep(1000);
        return ResultVO.success();
    }

    @GetMapping("/sendBroadcast")
    @RequiresAuthentication
    @ResponseBody
    public ResultVO<String> sendBroadcast(@RequestParam String message) throws Exception {
        log.info("发送公告：" + message);
        shellService.sendBroadcast(message);
        Thread.sleep(1000);
        return ResultVO.success();
    }

    @GetMapping("/getPlayerList")
    @RequiresAuthentication
    @ResponseBody
    public ResultVO<List<String>> getPlayerList() throws Exception {
        List<String> playerList = shellService.getPlayerList();
        return ResultVO.data(playerList);
    }

    @GetMapping("/kickPlayer")
    @RequiresAuthentication
    @ResponseBody
    public ResultVO<String> kickPlayer(@RequestParam String userId) {
        log.info("踢出玩家：" + userId);
        shellService.kickPlayer(userId);
        return ResultVO.success();
    }

    @GetMapping("/rollback")
    @RequiresAuthentication
    @ResponseBody
    public ResultVO<String> rollback(@RequestParam Integer dayNum) {
        log.info("回滚指定的天数：" + dayNum);
        shellService.rollback(dayNum);
        return ResultVO.success();
    }

    @GetMapping("/regenerate")
    @RequiresAuthentication
    @ResponseBody
    public ResultVO<String> regenerate() {
        log.info("重置世界...");
        shellService.regenerate();
        return ResultVO.success();
    }

    @PostMapping("/masterConsole")
    @ResponseBody
    @RequiresAuthentication
    public ResultVO<String> masterConsole(@RequestBody Map<String, String> param) {
        shellService.masterConsole(param.get("command"));
        return ResultVO.success();
    }

    @PostMapping("/cavesConsole")
    @ResponseBody
    @RequiresAuthentication
    public ResultVO<String> cavesConsole(@RequestBody Map<String, String> param) {
        shellService.cavesConsole(param.get("command"));
        return ResultVO.success();
    }

    @GetMapping("/playerOperate")
    @RequiresAuthentication
    @ResponseBody
    public ResultVO<String> playerOperate(@RequestParam String userId, @RequestParam String type) throws Exception {
        log.info("执行高级针对玩家的操作：type={},userId={}", type, userId);
        shellService.playerOperate(type, userId);
        return ResultVO.success();
    }

    @GetMapping("/delMyDediServer")
    @RequiresAuthentication
    @ResponseBody
    public ResultVO<String> delMyDediServer() {
        log.info("删除MyDediServer目录");
        homeService.delMyDediServer();
        return ResultVO.success();
    }

    @GetMapping("/delCavesRecord")
    @RequiresAuthentication
    @ResponseBody
    public ResultVO<String> onlyDelSave() {
        log.info("删除地面存档记录");
        homeService.onlyDelSave();
        return ResultVO.success();
    }

    @GetMapping("/getGameArchive")
    @ResponseBody
    @RequiresAuthentication
    public ResultVO<GameArchiveVO> getGameArchive() throws Exception {
        return ResultVO.data(homeService.getGameArchive());
    }



    @Autowired
    public void setHomeService(HomeService homeService) {
        this.homeService = homeService;
    }

    @Autowired
    public void setShellService(ShellService shellService) {
        this.shellService = shellService;
    }

    @Autowired
    public void setBackupService(BackupService backupService) {
        this.backupService = backupService;
    }
}
