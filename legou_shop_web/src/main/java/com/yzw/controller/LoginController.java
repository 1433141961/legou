package com.yzw.controller;

import com.yzw.entity.Result;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录功能
 */


@RestController
@RequestMapping("/login")
public class LoginController {

    @RequestMapping("/findLoginname")
    public Result findLoginname(){
        try {
            //创建security上下文所有者对象
            SecurityContextHolder sc = new SecurityContextHolder();
            //获得上下文对象
            SecurityContext context = sc.getContext();
            //获得name
            String userName = context.getAuthentication().getName();
            //查询name

            return new Result(true,"登录成功",userName);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,"登录成功");

        }

    }

}
