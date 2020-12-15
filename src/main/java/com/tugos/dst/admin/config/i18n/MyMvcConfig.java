package com.tugos.dst.admin.config.i18n;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description: 自定义WebMvc配置类
 * @Date: 2020/12/11 11:58
 * @Version: 1.0
 * @Author: Helb
 */
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {

    @Bean  //自定义国际化解析
    public LocaleResolver localeResolver(){
        return new MyLocalResolve();
    }

}