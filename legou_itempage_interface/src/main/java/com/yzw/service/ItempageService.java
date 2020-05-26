package com.yzw.service;


import com.yzw.entity.Goods;

import java.util.List;

/**
 * 商品详情服务接口
 */
public interface ItempageService {

    Goods findByGoodsId(Long goodsId);

    List<Goods> findAll();
}
