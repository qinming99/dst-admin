package com.tugos.dst.admin.controller;

import com.tugos.dst.admin.common.ResultVO;
import com.tugos.dst.admin.config.I18nResourcesConfig;
import com.tugos.dst.admin.entity.Menu;
import com.tugos.dst.admin.entity.User;
import com.tugos.dst.admin.utils.URL;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author qinming
 * @date 2020-05-16
 * <p> dst主页 </p>
 */
@Controller
public class MainController {

    /**
     * 后台主体内容
     */
    @GetMapping("/")
    @RequiresPermissions("index")
    public String main(Model model) {
        User user = new User();
        user.setNickname(I18nResourcesConfig.getMessage("main.user.name"));
        model.addAttribute("user", user);
        Menu menu = Menu.builder().id(2L).icon("layui-icon layui-icon-home").sort(0).
                children(new HashMap<>()).title(I18nResourcesConfig.getMessage("main.menu1.name")).type(1).url("/home/index").build();

        Menu menu1 = Menu.builder().id(2L).icon("layui-icon layui-icon-set").sort(1).
                children(new HashMap<>()).title(I18nResourcesConfig.getMessage("main.menu2.name")).type(1).url("/setting/index").build();

        Menu menu2 = Menu.builder().id(4L).icon("layui-icon layui-icon-group").sort(2).
                children(new HashMap<>()).title(I18nResourcesConfig.getMessage("main.menu3.name")).type(1).url("/player/index").build();

        Menu menu3 = Menu.builder().id(2L).icon("layui-icon layui-icon-log").sort(3).
                children(new HashMap<>()).title(I18nResourcesConfig.getMessage("main.menu4.name")).type(1).url("/backup/index").build();

        Menu menu4 = Menu.builder().id(3L).icon("layui-icon layui-icon-survey").sort(4).
                children(new HashMap<>()).title(I18nResourcesConfig.getMessage("main.menu5.name")).type(1).url("/system/guide").build();

        Menu menu5 = Menu.builder().id(4L).icon("layui-icon layui-icon-util").sort(5).
                children(new HashMap<>()).title(I18nResourcesConfig.getMessage("main.menu6.name")).type(1).url("/system/index").build();

        Map<String, Menu> treeMenu = new HashMap<>(16);
        treeMenu.put("0", menu);
        treeMenu.put("1", menu1);
        treeMenu.put("2", menu2);
        treeMenu.put("3", menu3);
        treeMenu.put("4", menu4);
        treeMenu.put("5", menu5);

        Locale locale = LocaleContextHolder.getLocale();
        if (Locale.CHINA.getLanguage().equals(locale.getLanguage())) {
            Menu menu6 = Menu.builder().id(5L).icon("layui-icon layui-icon-about").sort(6).
                    children(new HashMap<>()).title("关于").type(1).url("/system/about").build();
            treeMenu.put("6", menu6);
        }
        model.addAttribute("treeMenu", treeMenu);
        return "main";
    }


    /**
     * 跳转到个人信息页面
     */
    @GetMapping("/userInfo")
    @RequiresPermissions("index")
    public String toUserInfo(Model model) {
        return "system/main/userInfo";
    }


    /**
     * 保存修改个人信息
     */
    @PostMapping("/userInfo")
    @RequiresPermissions("index")
    @ResponseBody
    public ResultVO userInfo(User user) {

        // 复制保留无需修改的数据
        return ResultVO.data(new URL("/userInfo"));
    }

    /**
     * 跳转到修改密码页面
     */
    @GetMapping("/editPwd")
    @RequiresPermissions("index")
    public String toEditPwd() {
        return "system/main/editPwd";
    }

    /**
     * 保存修改密码
     */
    @PostMapping("/editPwd")
    @RequiresPermissions("index")
    @ResponseBody
    public ResultVO editPwd(String original, String password, String confirm) {
        // 判断原来密码是否有误

        return ResultVO.success();
    }
}
