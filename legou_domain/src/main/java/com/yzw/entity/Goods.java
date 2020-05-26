package com.yzw.entity;

import com.yzw.domain.TbGoods;
import com.yzw.domain.TbGoodsDesc;
import com.yzw.domain.TbItem;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Auther:Mr.元 1433141961@qq.com
 * @Date：2019/12/2 11:28
 * @Description:商品组合类
 */

public class Goods implements Serializable {

    private TbGoods tbGoods;

    private TbGoodsDesc tbGoodsDesc;

    private List<TbItem> itemList;

    private Map<String,String> categoryMap;

    public Map<String, String> getCategoryMap() {
        return categoryMap;
    }

    public void setCategoryMap(Map<String, String> categoryMap) {
        this.categoryMap = categoryMap;
    }

    public TbGoods getTbGoods() {
        return tbGoods;
    }

    public void setTbGoods(TbGoods tbGoods) {
        this.tbGoods = tbGoods;
    }

    public TbGoodsDesc getTbGoodsDesc() {
        return tbGoodsDesc;
    }

    public void setTbGoodsDesc(TbGoodsDesc tbGoodsDesc) {
        this.tbGoodsDesc = tbGoodsDesc;
    }

    public List<TbItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<TbItem> itemList) {
        this.itemList = itemList;
    }
}
