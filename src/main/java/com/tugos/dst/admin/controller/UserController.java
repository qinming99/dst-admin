package com.tugos.dst.admin.controller;


import com.tugos.dst.admin.common.ResultVO;
import com.tugos.dst.admin.entity.User;
import com.tugos.dst.admin.utils.DstConfigData;
import com.tugos.dst.admin.vo.UpdatePwdVO;
import com.tugos.dst.admin.vo.UpdateUserDetailVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author qinming
 * @date 2020-05-16
 * <p> 用户管理控制器 </p>
 */
@Controller
@RequestMapping("/system/user")
public class UserController {

    /**
     * 用户信息页
     */
    @GetMapping("/detail")
    @RequiresAuthentication
    public String detail(Model model) {
        UpdateUserDetailVO user = new UpdateUserDetailVO();
        //user.setUsername("root");
        //user.setNickname("尖角");
        model.addAttribute("user", user);
        return "/system/user/detail";
    }

    /**
     * 修改密码页
     */
    @GetMapping("/updatePwd")
    @RequiresAuthentication
    public String updatePwd() {
        return "/system/user/updatePwd";
    }

    @PostMapping("/setNewPwd")
    @RequiresAuthentication
    @ResponseBody
    public ResultVO setNewPwd(@RequestBody UpdatePwdVO vo) {
        User userInfo = (User) SecurityUtils.getSubject().getPrincipal();
        if (StringUtils.isAnyBlank(vo.getOldPwd(),vo.getNewPwd(),vo.getConfirmPwd())){
            return ResultVO.fail("密码不能为空");
        }
        if (!userInfo.getPassword().equals(vo.getOldPwd())){
            return ResultVO.fail("旧密码错误");
        }
        if (!vo.getNewPwd().equals(vo.getConfirmPwd())){
            return ResultVO.fail("两次密码不一致");
        }
        DstConfigData.USER_INFO.setPassword(vo.getNewPwd());
        //退出登录
        SecurityUtils.getSubject().logout();
        return ResultVO.success("修改成功");
    }

    @PostMapping("/setNewUserDetail")
    @RequiresAuthentication
    @ResponseBody
    public ResultVO setNewUserDetail(@RequestBody UpdateUserDetailVO vo) {
        User userInfo = (User) SecurityUtils.getSubject().getPrincipal();
        if (StringUtils.isAnyBlank(vo.getNickname(),vo.getPicture(),vo.getUsername())){
            return ResultVO.fail("信息不能为空");
        }

        DstConfigData.USER_INFO.setNickname(vo.getPicture());
        DstConfigData.USER_INFO.setUsername(vo.getUsername());
        DstConfigData.USER_INFO.setPicture(vo.getPicture());
        //退出登录
        return ResultVO.success("修改成功");
    }


    /**
     * 获取用户头像
     */
    @GetMapping("/picture")
    public void picture(String userName, HttpServletResponse response) throws IOException {
        String defaultPath = "/images/user-picture.jpg";
        Resource resource = new ClassPathResource("static" + defaultPath);
        FileCopyUtils.copy(resource.getInputStream(), response.getOutputStream());
    }



}
