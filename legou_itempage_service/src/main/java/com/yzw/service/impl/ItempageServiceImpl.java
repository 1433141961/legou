package com.yzw.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.yzw.domain.TbGoods;
import com.yzw.domain.TbGoodsDesc;
import com.yzw.domain.TbItem;
import com.yzw.domain.TbItemExample;
import com.yzw.entity.Goods;
import com.yzw.mapper.TbGoodsDescMapper;
import com.yzw.mapper.TbGoodsMapper;
import com.yzw.mapper.TbItemCatMapper;
import com.yzw.mapper.TbItemMapper;
import com.yzw.service.ItempageService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Athor:Yuan
 * @Date:2020/3/5$ 10:19$
 * Desc:商品详情页服务
 */
@Service
public class ItempageServiceImpl implements ItempageService {
    @Autowired
    private TbGoodsMapper tbGoodsMapper;
    @Autowired
    private TbGoodsDescMapper tbGoodsDescMapper;
    @Autowired
    private TbItemCatMapper tbItemCatMapper;
    @Autowired
    private TbItemMapper tbItemMapper;


    @Override
    public Goods findByGoodsId(Long goodsId) {
        // 创建Goods对象，封装数据
        Goods goods = new Goods();
        // 先查询TbGoods数据
        TbGoods tbGoods = tbGoodsMapper.selectByPrimaryKey(goodsId);
        goods.setTbGoods(tbGoods);

        Map<String,String> categoryMap = new HashMap<>();
        categoryMap.put("category1",tbItemCatMapper.selectByPrimaryKey(tbGoods.getCategory1Id()).getName());
        categoryMap.put("category2",tbItemCatMapper.selectByPrimaryKey(tbGoods.getCategory2Id()).getName());
        categoryMap.put("category3",tbItemCatMapper.selectByPrimaryKey(tbGoods.getCategory3Id()).getName());
        goods.setCategoryMap(categoryMap);

        // 设置desc描述数据
        TbGoodsDesc tbGoodsDesc = tbGoodsDescMapper.selectByPrimaryKey(goodsId);
        goods.setTbGoodsDesc(tbGoodsDesc);

        // 查询sku数据
        TbItemExample example = new TbItemExample();
        TbItemExample.Criteria criteria = example.createCriteria();
        criteria.andGoodsIdEqualTo(goodsId);
        List<TbItem> tbItems = tbItemMapper.selectByExample(example);
        goods.setItemList(tbItems);

        return goods;
    }

    /**
     * 查询所有goods
     * @return
     */
    @Override
    public List<Goods> findAll() {
//        List<Goods> goodsList = new ArrayList<>();
//        List<TbGoods> tbGoodsList = tbGoodsMapper.selectByExample(null);
//        for (TbGoods tbGoods : tbGoodsList) {
//            Goods goods = findByGoodsId(tbGoods.getId());
//            goodsList.add(goods);
//        }
//        return goodsList;
        // 创建goods的集合，存储数据
        List<Goods> goodsList = new ArrayList<>();
        // 查询所有的spu数据
        List<TbGoods> tbGoods = tbGoodsMapper.selectByExample(null);
        // 遍历
        for (TbGoods tbGood : tbGoods) {
            Goods goods = findByGoodsId(tbGood.getId());
            goodsList.add(goods);
        }
        return goodsList;

    }
}
