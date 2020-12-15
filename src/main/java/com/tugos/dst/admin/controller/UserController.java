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
import java.util.regex.Pattern;

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
        User user = new User();
        user.setNickname("管理员");
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


    /**
     * 获取用户头像
     */
    @GetMapping("/picture")
    public void picture(String userName, HttpServletResponse response) throws IOException {
        String defaultPath = "/images/user-picture.jpg";
        Resource resource = new ClassPathResource("static" + defaultPath);
        FileCopyUtils.copy(resource.getInputStream(), response.getOutputStream());
    }

    @PostMapping("/setUserInfo")
    @RequiresAuthentication
    @ResponseBody
    public ResultVO setUserInfo(@RequestBody UpdateUserDetailVO vo) {
        User userInfo = (User) SecurityUtils.getSubject().getPrincipal();
        if (StringUtils.isAnyBlank(vo.getUserName())){
            return ResultVO.fail("用户名不能为空");
        }

        if (StringUtils.isNotEmpty(vo.getEmail())) {
            //利用正则表达式 验证邮箱是否符合邮箱的格式
            if(!vo.getEmail().matches("^\\w+@(\\w+\\.)+\\w+$")){
                return ResultVO.fail("邮箱格式不正确");
            }
        }

        if (StringUtils.isNotEmpty(vo.getPhone())) {
            if (!isPhone(vo.getPhone())) {
                return ResultVO.fail("手机号码格式不正确");
            }
        }

        if (StringUtils.isEmpty(vo.getNickName())){
            vo.setNickName("管理员");
        }
        if (StringUtils.isEmpty(vo.getHeadImg())){
            vo.setHeadImg("/images/user-picture.jpg");
        }


        return ResultVO.success("修改成功");
    }

    /**
     * @param phone 手机号码
     * @return 是否属于三大运营商号段范围
     */
    private static boolean isPhone(String phone) {
        String regex = "^((13[0-9])|(14[5-9])|(15([0-3]|[5-9]))|(16[6-7])|(17[1-8])|(18[0-9])|(19[1|5])|(19[6|8])|(199))\\d{8}$";
        Pattern p = Pattern.compile(regex);
        return p.matcher(phone).matches();
    }

}
