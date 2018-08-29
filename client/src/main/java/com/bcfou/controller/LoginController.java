package com.bcfou.controller;

import com.bcfou.utils.ShiroUtils;
import io.renren.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 * @Author: 编程否
 * @Date: 2018/8/26 16:48
 */
@RestController
@Slf4j
public class LoginController {


    /**
     * 登录方法
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public R login(HttpServletRequest request, String username, String password, boolean rememberme){
        try{
            Subject subject = ShiroUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberme);
            subject.login(token);
        }catch (UnknownAccountException e) {
            return R.error(e.getMessage());
        }catch (IncorrectCredentialsException e) {
            return R.error("账号或密码不正确");
        }catch (LockedAccountException e) {
            return R.error("账号已被锁定,请联系管理员");
        }catch (AuthenticationException e) {
            return R.error("账户验证失败");
        }
        SavedRequest savedRequest = WebUtils.getSavedRequest(request);
        String beforeUrl = null;
        if (savedRequest != null){
            beforeUrl = savedRequest.getRequestUrl();
        }
        return R.ok().put("beforeUrl", beforeUrl);
    }
}
