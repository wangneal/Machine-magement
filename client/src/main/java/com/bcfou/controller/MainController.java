package com.bcfou.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: 编程否
 * @Date: 2018/8/26 17:06
 */
@Controller
public class MainController {
    @GetMapping("/")
    public String index(){
        return "redirect:/index";
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @GetMapping("/index")
    public String index1(){
        return "index";
    }
}
