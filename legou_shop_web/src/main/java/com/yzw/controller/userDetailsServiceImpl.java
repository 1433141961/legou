package com.yzw.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yzw.domain.TbSeller;
import com.yzw.service.SellerService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * 商家登录认证服务类
 *
 * 要求：必须实现spring security框架的UserDetailsService的接口，否则无法认证
 */

//名字要和springsecurity.xml中配置的相同
//@Component("/userDetailsService")
public class userDetailsServiceImpl implements UserDetailsService {

    //调用sellerService的服务类
    @Reference
    private SellerService sellerService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //校验用户名.判断是否为空
        if (username == null || username.trim().isEmpty()){
            return null;
        }
        //根据用户名（seller表主键）查询
        TbSeller seller = sellerService.findOne(username);

        //校验是否有查到数据
        if (seller == null){
            return null;
        }
        //校验状态是否为审核通过状态
        if (!seller.getStatus().equals("1")){
            return null;
        }

        List<GrantedAuthority> list = new ArrayList<>();
        //可以添加r多个角色，但是必须要有配置文件里的角色
        list.add(new SimpleGrantedAuthority("ROLE_SELLER"));
        list.add(new SimpleGrantedAuthority("ROLE_USER"));
        //封装数据
        User user =new User(username,seller.getPassword(), list);
        return user;
    }
}
