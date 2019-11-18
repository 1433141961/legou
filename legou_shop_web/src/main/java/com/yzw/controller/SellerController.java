package com.yzw.controller;

import com.yzw.domain.TbSeller;
import com.yzw.entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seller")
public class SellerController {
    /**
     *
     * 保存商家入驻信息
     */
    @RequestMapping("/save")
    public Result save(@RequestBody TbSeller seller){

        try {
            //设置状态，0：表示未审核，1：表示审核通过，2：表示审核不通过
            //设置默认值为0
            seller.setStatus("0");

            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
