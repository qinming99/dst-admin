package com.tugos.dst.admin.controller;


import com.tugos.dst.admin.common.ResultVO;
import com.tugos.dst.admin.service.SettingService;
import com.tugos.dst.admin.vo.GameConfigVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * @author qinming
 * @date 2020-05-16
 * <p> 房间设置服务 </p>
 */
@Controller
@RequestMapping("/setting")
@Slf4j
public class SettingController {

    private SettingService settingService;

    @GetMapping("/index")
    @RequiresAuthentication
    public String index(HttpServletRequest request) {
        log.info("进入游戏设置页");
        Locale locale = LocaleContextHolder.getLocale();
        if (Locale.CHINA.getLanguage().equals(locale.getLanguage())) {
            request.setAttribute("lang", "zh");
        } else {
            request.setAttribute("lang", "en");
        }
        return "/setting/index";
    }

    @PostMapping("/saveConfig")
    @RequiresAuthentication
    @ResponseBody
    public ResultVO<String> saveConfig(@RequestBody GameConfigVO model) throws Exception {
        log.info("保存游戏配置，{}", StringUtils.deleteWhitespace(model.toString()));
        return settingService.saveConfig(model);
    }

    @GetMapping("/getConfig")
    @RequiresAuthentication
    @ResponseBody
    public ResultVO<GameConfigVO> getConfig() throws Exception {
        log.info("读取游戏配置");
        GameConfigVO config = settingService.getConfig();
        return ResultVO.data(config);
    }

    @Autowired
    public void setSettingService(SettingService settingService) {
        this.settingService = settingService;
    }
}
