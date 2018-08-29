package com.bcfou.config;

import com.bcfou.entity.User;
import com.bcfou.repository.UserRepository;
import com.bcfou.utils.ShiroUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 自己实现的shiro策略
 * @Author: 编程否
 * @Date: 2018/8/26 14:26
 */
@Slf4j
public class MyRealm extends AuthorizingRealm {
    @Autowired
    private UserRepository userRepository;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        String password = new String((char[]) token.getCredentials());
        log.info("【登录认证】用户{}正在登录",username);
        User user = userRepository.findByUsername(username);
        if (user == null){
            throw new UnknownAccountException("用户名或密码不正确");
        }
        if (!ShiroUtils.sha256(password, user.getSalt()).equals(user.getPassword())){
            throw new IncorrectCredentialsException("用户名或密码不正确");
        }if (user.getStatus()==0){
            throw new LockedAccountException("账户已被锁定");
        }
        return new SimpleAuthenticationInfo(user, password, getName());
    }
}
