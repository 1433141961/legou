package com.yzw.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yzw.domain.TbGoods;
import com.yzw.entity.Goods;
import com.yzw.entity.Result;
import com.yzw.service.GoodsService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品模块控制器
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Reference
    private GoodsService goodsService;

    /**
     * 保存商品
     * @param goods
     * @return
     */
    @RequestMapping("/save")
    public Result save(@RequestBody Goods goods){
        try {
            //获得商家id
            String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
            //获得商品类
            TbGoods tbGoods = goods.getTbGoods();
            //设置商家id（用于确定是谁上架的商品）
            tbGoods.setSellerId(sellerId);

            goodsService.add(goods);
            return new Result(true,"保存成功");
        } catch (Exception e) {
            e.printStackTrace();

            return new Result(false,"保存失败");
        }
    }



}
