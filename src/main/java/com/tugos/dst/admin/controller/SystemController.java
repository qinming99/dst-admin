package com.tugos.dst.admin.controller;

import com.tugos.dst.admin.common.ResultVO;
import com.tugos.dst.admin.service.SystemService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author qinming
 * @date 2020-10-25 10:57:45
 * <p> 系统服务器控制器 </p>
 */
@Controller
@Slf4j
@RequestMapping("/system")
public class SystemController {

    private SystemService systemService;

    /**
     * 系统设置页
     */
    @GetMapping("/index")
    @RequiresAuthentication
    public String index() {
        return "system/index";
    }

    /**
     * 向导页面
     */
    @GetMapping("/guide")
    public String toGuide() {
        return "system/guide";
    }

    @GetMapping("/getDstLog")
    @ResponseBody
    public ResultVO<List<String>> getDstLog(@RequestParam(required = false, defaultValue = "0") Integer type,
                                            @RequestParam(required = false, defaultValue = "100") Integer rowNum) {
        log.info("拉取饥荒的日志：type={},rowNum={}", type, rowNum);
        return ResultVO.data(systemService.getDstLog(type, rowNum));
    }

    @Autowired
    public void setSystemService(SystemService systemService) {
        this.systemService = systemService;
    }
}
