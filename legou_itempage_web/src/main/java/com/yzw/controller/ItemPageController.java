package com.yzw.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yzw.domain.TbItem;
import com.yzw.entity.Goods;
import com.yzw.entity.Result;
import com.yzw.service.ItempageService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Athor:Yuan
 * @Date:2020/3/5$ 10:12$
 * Desc:
 */
@RestController
@RequestMapping("/itempage")
public class ItemPageController {
    @Autowired
    private FreeMarkerConfig freeMarkerConfig;

    @Reference
    private ItempageService itempageService;

    @RequestMapping("/genHtmlByGoodsId/{goodsId}")
    public Result genHtmlByGoodsId(@PathVariable("goodsId") Long goodsId){
        try {
            // 查询goods数据
            Goods goods = itempageService.findByGoodsId(goodsId);
            // 获取sku数据
            List<TbItem> itemList = goods.getItemList();
            // 创建配置
            Configuration configuration = freeMarkerConfig.getConfiguration();

            // 获取模板
            Template template = configuration.getTemplate("item.ftl");

            // 遍历 sku数据
            for (TbItem tbItem : itemList) {
                // 创建map集合，存储数据
                Map<String,Object> map = new HashMap<>();
                map.put("goods",goods);
                map.put("tbItem",tbItem);
                // 创建输出流
                FileWriter writer = new FileWriter("D:\\htmls\\"+tbItem.getId()+".html");
                // 生成html
                template.process(map,writer);
                // 关闭流
                writer.close();
            }

            // 返回结果
            return new Result(true,"生成成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,"生成失败");
        }

    }

    @RequestMapping("/genAllHtml")
    public Result genAllHtml(){
            try {
                // 查询所有goods数据
                List<Goods> goodsList = itempageService.findAll();
                // 遍历
                for (Goods goods : goodsList) {
                    // 获取sku数据
                    List<TbItem> itemList = goods.getItemList();
                    // 创建配置
                    Configuration configuration = freeMarkerConfig.getConfiguration();

                    // 获取模板
                    Template template = configuration.getTemplate("item.ftl");

                    // 遍历 sku数据
                    for (TbItem tbItem : itemList) {
                        // 创建map集合，存储数据
                        Map<String,Object> map = new HashMap<>();
                        map.put("goods",goods);
                        map.put("tbItem",tbItem);
                        // 创建输出流
                        FileWriter writer = new FileWriter("D:\\htmls\\"+tbItem.getId()+".html");
                        // 生成html
                        template.process(map,writer);
                        // 关闭流
                        writer.close();
                    }
                }

                // 返回结果
            return new Result(true,"生成成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,"生成失败");
        }

    }
}
