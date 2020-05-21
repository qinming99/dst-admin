package com.tugos.dst.admin.controller;

import com.tugos.dst.admin.service.ModMapService;
import com.tugos.dst.admin.utils.ResultVoUtil;
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
@RequestMapping("/mod")
@Slf4j
public class ModController {

    @Autowired
    private ModMapService modMapService;


    @GetMapping("/index")
    @RequiresPermissions("mod:index")
    public String index(Model model) throws Exception {
        String modData = modMapService.getModData();
        String masterMapData = modMapService.getMasterMapData();
        String cavesMapData = modMapService.getCavesMapData();
        model.addAttribute("modData", modData);
        model.addAttribute("masterMapData", masterMapData);
        model.addAttribute("cavesMapData", cavesMapData);
        return "/mod/index";
    }


    @PostMapping("/saveMod")
    @RequiresPermissions("mod:saveMod")
    @ResponseBody
    public ResultVo saveMod(String data) throws Exception {
        log.info(data);
        modMapService.saveMod(data);
        return ResultVoUtil.success();
    }

    @PostMapping("/saveMaster")
    @RequiresPermissions("mod:saveMaster")
    @ResponseBody
    public ResultVo saveMaster(String data) throws Exception {
        log.info(data);
        modMapService.saveMaster(data);
        return ResultVoUtil.success();
    }

    @PostMapping("/saveCaves")
    @RequiresPermissions("mod:saveCaves")
    @ResponseBody
    public ResultVo saveCaves(String data) throws Exception {
        log.info(data);
        modMapService.saveCaves(data);
        return ResultVoUtil.success();
    }

}
