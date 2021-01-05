package com.tugos.dst.admin.controller;


import com.tugos.dst.admin.common.ResultCodeEnum;
import com.tugos.dst.admin.common.ResultVO;
import com.tugos.dst.admin.config.I18nResourcesConfig;
import com.tugos.dst.admin.exception.ResultException;
import com.tugos.dst.admin.utils.CaptchaUtil;
import com.tugos.dst.admin.utils.URL;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author qinming
 * @date 2020-05-16
 * <p> 登陆器 </p>
 */
@Controller
public class LoginController {


    @Value("${project.captcha-open:false}")
    private boolean isCaptcha;

    /**
     * 跳转到登录页面
     */
    @GetMapping("/login")
    public String toLogin(Model model) {
        model.addAttribute("isCaptcha", isCaptcha);
        return "login";
    }


    /**
     * 实现登录
     */
    @PostMapping("/login")
    @ResponseBody
    public ResultVO login(String username, String password, String captcha, String rememberMe) {
        // 判断账号密码是否为空
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            throw new ResultException(ResultCodeEnum.USER_NAME_PWD_NULL);
        }
        // 判断验证码是否正确
        if (isCaptcha) {
            Session session = SecurityUtils.getSubject().getSession();
            String sessionCaptcha = (String) session.getAttribute("captcha");
            if (StringUtils.isEmpty(captcha) || StringUtils.isEmpty(sessionCaptcha)
                    || !captcha.toUpperCase().equals(sessionCaptcha.toUpperCase())) {
                throw new ResultException(ResultCodeEnum.USER_CAPTCHA_ERROR);
            }
            session.removeAttribute("captcha");
        }
        // 1.获取Subject主体对象
        Subject subject = SecurityUtils.getSubject();
        // 2.封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        // 3.执行登录，进入自定义Realm类中
        try {
            // 判断是否自动登录
            if (rememberMe != null) {
                token.setRememberMe(true);
            } else {
                token.setRememberMe(false);
            }
            //登录
            subject.login(token);
            //登录成功，跳转至首页
            ResultVO<URL> data = ResultVO.data(new URL("/"));
            data.setMessage(I18nResourcesConfig.getMessage("tip.login.success"));
            return data;
        } catch (AuthenticationException e) {
            return ResultVO.fail(I18nResourcesConfig.getMessage("tip.login.fail"));
        } catch (Exception e) {
            return ResultVO.fail(I18nResourcesConfig.getMessage("tip.login.error"));
        }
    }

    /**
     * 验证码图片
     */
    @GetMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //设置响应头信息，通知浏览器不要缓存
        response.setHeader("Expires", "-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "-1");
        response.setContentType("image/jpeg");
        // 获取验证码
        String code = CaptchaUtil.getRandomCode();
        // 将验证码输入到session中，用来验证
        request.getSession().setAttribute("captcha", code);
        // 输出到web页面
        ImageIO.write(CaptchaUtil.genCaptcha(code), "jpg", response.getOutputStream());
    }

    /**
     * 退出登录
     */
    @GetMapping("/logout")
    public String logout() {
        SecurityUtils.getSubject().logout();
        return "redirect:/login";
    }

    /**
     * 权限不足页面
     */
    @GetMapping("/noAuth")
    public String noAuth() {
        return "system/main/noAuth";
    }

}
