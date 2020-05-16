package com.tugos.dst.admin.shiro;


import com.tugos.dst.admin.entity.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.PostConstruct;

public class AuthRealm extends AuthorizingRealm {

    /**
     * 授权逻辑
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 管理员拥有所有权限
        info.addRole("admin");
        info.addStringPermission("*:*:*");
        return info;
    }

    /**
     * 认证逻辑
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        User user = new User();
        user.setUsername(token.getUsername());
        user.setPassword(String.valueOf(token.getPassword()));
        return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
    }

    /**
     * 自定义密码验证匹配器
     */
    @PostConstruct
    public void initCredentialsMatcher() {
        setCredentialsMatcher(new SimpleCredentialsMatcher() {
            @Override
            public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {
                UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
                SimpleAuthenticationInfo info = (SimpleAuthenticationInfo) authenticationInfo;
                // 获取明文密码及密码盐
                String password = String.valueOf(token.getPassword());
                String username = token.getUsername();
                if ("dst".equals(password)){
                    return true;
                }
                return false;
            }
        });
    }
}
