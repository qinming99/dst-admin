package com.tugos.dst.admin.controller;

import com.tugos.dst.admin.common.ResultVO;
import com.tugos.dst.admin.service.PlayerService;
import com.tugos.dst.admin.vo.PlayerSettingVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author qinming
 * @date 2020-11-16 22:46:07
 * <p> 玩家设置，设置黑白名单，管理员 </p>
 */
@Controller
@RequestMapping("/player")
@Slf4j
public class PlayerController {

    private PlayerService playerService;

    @GetMapping("/index")
    @RequiresAuthentication
    public String index() {
        log.info("进入玩家设置页面");
        return "/player/index";
    }

    @GetMapping("/getDstAdminList")
    @RequiresAuthentication
    @ResponseBody
    public ResultVO<List<String>> getDstAdminList() {
        log.info("拉取管理员列表");
        return ResultVO.data(playerService.getDstAdminList());
    }

    @GetMapping("/getDstBlacklist")
    @RequiresAuthentication
    @ResponseBody
    public ResultVO<List<String>> getDstBlacklist() {
        log.info("拉取玩家黑名单列表");
        return ResultVO.data(playerService.getDstBlacklist());
    }

    @PostMapping("/saveAdminList")
    @RequiresAuthentication
    @ResponseBody
    @Deprecated
    public ResultVO<String> saveAdminList(@RequestBody List<String> adminList) throws Exception {
        log.info("保存管理员：" + adminList);
        return playerService.saveAdminList(adminList);
    }

    @PostMapping("/saveBlackList")
    @RequiresAuthentication
    @ResponseBody
    @Deprecated
    public ResultVO<String> saveBlackList(@RequestBody List<String> blackList) throws Exception {
        log.info("保存黑名单：" + blackList);
        return playerService.saveBlackList(blackList);
    }

    @PostMapping("/saveAdminAndBlackList")
    @RequiresAuthentication
    @ResponseBody
    public ResultVO<String> saveAdminAndBlackList(@RequestBody PlayerSettingVO playerSettingVO) throws Exception {
        log.info("保存管理员和黑名单：" + playerSettingVO);
        playerService.saveAdminList(playerSettingVO.getAdminList());
        playerService.saveBlackList(playerSettingVO.getBlackList());
        return ResultVO.success();
    }


    @Autowired
    public void setPlayerService(PlayerService playerService) {
        this.playerService = playerService;
    }
}
