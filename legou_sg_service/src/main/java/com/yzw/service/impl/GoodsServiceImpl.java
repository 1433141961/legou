package com.yzw.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.yzw.domain.TbGoods;
import com.yzw.domain.TbGoodsDesc;
import com.yzw.domain.TbItem;
import com.yzw.domain.TbItemCat;
import com.yzw.entity.Goods;
import com.yzw.mapper.*;
import com.yzw.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 商品服务实现类
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private TbGoodsMapper tbGoodsMapper;

    @Autowired
    private TbGoodsDescMapper tbGoodsDescMapper;
    @Autowired
    private TbItemMapper tbItemMapper;

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private TbSellerMapper tbSellerMapper;

    @Autowired
    private TbItemCatMapper tbItemCatMapper;
    /**
     * 保存商品
     * @param goods
     */
    @Transactional
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
        //获得商品sku信息集合
        List<TbItem> itemList = goods.getItemList();
        if (itemList!= null && itemList.size()>0){
            for (TbItem tbItem : itemList) {
                //补全数据
                //设置品牌名称
                tbItem.setBrand(brandMapper.findOne(tbGoods.getBrandId()).getName());
                //设置商家id
                tbItem.setSellerId(tbGoods.getSellerId());
                //设置商家名
                tbItem.setSeller(tbSellerMapper.selectByPrimaryKey(tbGoods.getSellerId()).getName());
                //设置spu的id
                tbItem.setGoodsId(tbGoods.getId());
                //设置商品分类名称，（保存的是三级分类）
                tbItem.setCategory(tbItemCatMapper.selectByPrimaryKey(tbGoods.getCategory3Id()).getName());

                //获得图片的信息
                //           [{"color":"白色","url":"http://192.168.25.133/group1/M00/00/00/wKgZhVmNXEWAWuHOAAjlKdWCzvg949.jpg"},
                //            {"color":"黑色","url":"http://192.168.25.133/group1/M00/00/00/wKgZhVmNXEuAB_ujAAETwD7A1Is158.jpg"}]
                String itemImages = tbGoodsDesc.getItemImages();

                if (itemImages!=null && !itemImages.isEmpty()){
                    //解析json串
                    List<Map> itemImagesList = JSON.parseArray(itemImages, Map.class);
                    //获得第一个
                    Map map = itemImagesList.get(0);
                    //获得url
                    String url = (String) map.get("url");
                    //设置图片信息,默认只保存第一张图片的url
                    tbItem.setImage(url);
                }

                //设置创建时间
                tbItem.setCreateTime(new Date());
                //设置更新时间
                tbItem.setUpdateTime(new Date());

                //拼接商品标题：spu名+机身内存+网络+。。。
                String goodsName = tbGoods.getGoodsName();
                //获得规格信息{"机身内存":"16G","网络":"联通3G"}
                String specs = tbItem.getSpec();
                if (specs!= null && !specs.isEmpty()){
                    Map map = JSON.parseObject(specs, Map.class);
                    for (Object o : map.keySet()) {
                        goodsName += " " + o + " " + map.get(o);
                    }
                }
                //设置商品的标题spu名+规格属性)
                tbItem.setTitle(goodsName);

                //设置类别id
                tbItem.setCategoryid(tbGoods.getCategory3Id());
                //插入一条sku数据
                tbItemMapper.insert(tbItem);
            }
        }

    }

}
