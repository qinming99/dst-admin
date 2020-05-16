package com.tugos.dst.admin.controller;

import com.tugos.dst.admin.entity.Menu;
import com.tugos.dst.admin.entity.User;
import com.tugos.dst.admin.utils.ResultVoUtil;
import com.tugos.dst.admin.utils.URL;
import com.tugos.dst.admin.vo.ResultVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;


@Controller
public class MainController {

    /**
     * 后台主体内容
     */
    @GetMapping("/")
    @RequiresPermissions("index")
    public String main(Model model) {
        User user = new User();
        user.setNickname("管理员");
        model.addAttribute("user", user);
        Menu menu = Menu.builder().id(2L).icon("layui-icon layui-icon-home").sort(0).
                children(new HashMap<>()).title("主页").type(1).url("/index").build();
        Menu menu2 = Menu.builder().id(2L).icon("layui-icon layui-icon-util").sort(1).
                children(new HashMap<>()).title("设置").type(1).url("/setting/index").build();
        Menu menu3 = Menu.builder().id(2L).icon("layui-icon layui-icon-console").sort(2).
                children(new HashMap<>()).title("系统状态").type(1).url("/monitor/index").build();
        Map<Long, Menu> treeMenu = new HashMap<>(16);
        treeMenu.put(0L, menu);
        treeMenu.put(1L, menu2);
        treeMenu.put(2L, menu3);
        model.addAttribute("treeMenu", treeMenu);
        return "/main";
    }


    /**
     * 跳转到个人信息页面
     */
    @GetMapping("/userInfo")
    @RequiresPermissions("index")
    public String toUserInfo(Model model) {
        return "/system/main/userInfo";
    }


    /**
     * 保存修改个人信息
     */
    @PostMapping("/userInfo")
    @RequiresPermissions("index")
    @ResponseBody
    public ResultVo userInfo(User user) {

        // 复制保留无需修改的数据

        return ResultVoUtil.success("保存成功", new URL("/userInfo"));
    }

    /**
     * 跳转到修改密码页面
     */
    @GetMapping("/editPwd")
    @RequiresPermissions("index")
    public String toEditPwd() {
        return "/system/main/editPwd";
    }

    /**
     * 保存修改密码
     */
    @PostMapping("/editPwd")
    @RequiresPermissions("index")
    @ResponseBody
    public ResultVo editPwd(String original, String password, String confirm) {
        // 判断原来密码是否有误

        return ResultVoUtil.success("修改成功");
    }
}
