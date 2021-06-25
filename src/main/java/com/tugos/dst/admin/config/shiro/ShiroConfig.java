package com.tugos.dst.admin.config.shiro;


import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * @author qinming
 * @date 2020-5-16
 * <p> shiro配置 </p>
 */
@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager securityManager, ShiroProjectProperties properties) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        HashMap<String, Filter> myFilters = new HashMap<>(16);
        myFilters.put("userAuth", new UserAuthFilter());
        shiroFilterFactoryBean.setFilters(myFilters);
        LinkedHashMap<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/login", "anon");
        filterMap.put("/logout", "anon");
        //忽略websocket
        filterMap.put("/websocket", "anon");
        filterMap.put("/captcha", "anon");
        filterMap.put("/noAuth", "anon");
        filterMap.put("/css/**", "anon");
        filterMap.put("/js/**", "anon");
        filterMap.put("/images/**", "anon");
        filterMap.put("/lib/**", "anon");
        filterMap.put("/favicon.ico", "anon");
        // 通过配置文件方式配置的[anon]忽略规则
        String[] excludes = properties.getExcludes().split(",");
        for (String exclude : excludes) {
            if (!StringUtils.isEmpty(exclude.trim())) {
                filterMap.put(exclude, "anon");
            }
        }
        // 拦截根目录下所有路径，需要放行的路径必须在之前添加
        filterMap.put("/**", "userAuth");
        // 设置过滤规则
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        // 设置登录页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 未授权错误页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/noAuth");
        return shiroFilterFactoryBean;
    }

    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(AuthRealm authRealm,
                                                                  DefaultWebSessionManager sessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(authRealm);
        securityManager.setSessionManager(sessionManager);
        return securityManager;
    }

    /**
     * 自定义的Realm
     */
    @Bean
    public AuthRealm getRealm() {
        AuthRealm authRealm = new AuthRealm();
        return authRealm;
    }


    /**
     * session管理器
     */
    @Bean
    public DefaultWebSessionManager getDefaultWebSessionManager(ShiroProjectProperties properties) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setGlobalSessionTimeout(properties.getGlobalSessionTimeout() * 1000);
        sessionManager.setSessionValidationInterval(properties.getSessionValidationInterval() * 1000);
        sessionManager.setDeleteInvalidSessions(true);
        sessionManager.validateSessions();
        // 去掉登录页面地址栏jsessionid
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        return sessionManager;
    }

    /**
     * 启用shrio授权注解拦截方式，AOP式方法级权限检查
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor =
                new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

}
