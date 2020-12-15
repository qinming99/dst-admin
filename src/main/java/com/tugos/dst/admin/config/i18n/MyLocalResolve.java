package com.tugos.dst.admin.config.i18n;


import cn.hutool.core.util.StrUtil;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * @Description: 自定义地区解析器
 * @Date: 2020/12/11 12:00
 * @Version: 1.0
 * @Author: Helb
 */
public class MyLocalResolve implements LocaleResolver {
    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        Locale locale = null;
        //获取参数 locale=en_US
        String localeParameter = request.getParameter("locale");
        if(StrUtil.isNotEmpty(localeParameter)){
            //en_US通过下划线分割为数组
            String[] paraArr = localeParameter.split("_");
            locale = new Locale(paraArr[0],paraArr[1]);
        }else{
            //获取默认 就是浏览器中地区语言信息
            locale = request.getLocale();
        }
        return locale;
    }
    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {

    }
}