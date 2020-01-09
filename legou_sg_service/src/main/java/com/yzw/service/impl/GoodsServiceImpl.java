package com.yzw.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.yzw.domain.TbGoods;
import com.yzw.domain.TbGoodsDesc;
import com.yzw.entity.Goods;
import com.yzw.mapper.TbGoodsDescMapper;
import com.yzw.mapper.TbGoodsMapper;
import com.yzw.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 商品服务实现类
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private TbGoodsMapper tbGoodsMapper;

    @Autowired
    private TbGoodsDescMapper tbGoodsDescMapper;

    /**
     * 保存商品
     * @param goods
     */
    @Override
    public void add(Goods goods) {

        TbGoods tbGoods = goods.getTbGoods();
        //保存，主键自增（select last_insert_id()）
        tbGoodsMapper.insert(tbGoods);
        //获得商品描述
        TbGoodsDesc tbGoodsDesc = goods.getTbGoodsDesc();
        //为商品描述设置上商品id（他们之间是一对一的关系。
        // 我们设计表结构的时候没有主外键约束，所以手动实现这种逻辑，让goods表中的主键作为goodsdesc表的主键）
        tbGoodsDesc.setGoodsId(tbGoods.getId());
        tbGoodsDescMapper.insert(tbGoodsDesc);
    }

}
