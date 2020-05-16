package com.tugos.dst.admin.controller;


import com.tugos.dst.admin.entity.User;
import com.tugos.dst.admin.utils.ResultVoUtil;
import com.tugos.dst.admin.vo.ResultVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@Controller
@RequestMapping("/system/user")
public class UserController {



    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("system:user:add")
    public String toAdd() {
        return "/system/user/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("system:user:edit")
    public String toEdit(@PathVariable("id") User user, Model model) {
        model.addAttribute("user", user);
        return "/system/user/add";
    }

    /**
     * 保存添加/修改的数据
     * @param user 实体对象
     */
    @PostMapping("/save")
    @RequiresPermissions({"system:user:add", "system:user:edit"})
    @ResponseBody
    public ResultVo save( User user) {


        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("system:user:detail")
    public String toDetail(@PathVariable("id") User user, Model model) {
        model.addAttribute("user", user);
        return "/system/user/detail";
    }

    /**
     * 跳转到修改密码页面
     */
    @GetMapping("/pwd")
    @RequiresPermissions("system:user:pwd")
    public String toEditPassword(Model model, @RequestParam(value = "ids", required = false) List<Long> ids) {
        model.addAttribute("idList", ids);
        return "/system/user/pwd";
    }

    /**
     * 修改密码
     */
    @PostMapping("/pwd")
    @RequiresPermissions("system:user:pwd")
    @ResponseBody
    public ResultVo editPassword(String password, String confirm,
                                 @RequestParam(value = "ids", required = false) List<Long> ids,
                                 @RequestParam(value = "ids", required = false) List<User> users) {


        return ResultVoUtil.success("修改成功");
    }


    /**
     * 获取用户头像
     */
    @GetMapping("/picture")
    public void picture(String p, HttpServletResponse response) throws IOException {
        String defaultPath = "/images/user-picture.jpg";
        Resource resource = new ClassPathResource("static" + defaultPath);
        FileCopyUtils.copy(resource.getInputStream(), response.getOutputStream());
    }



}
