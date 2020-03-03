package com.yzw.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yzw.entity.Result;
import com.yzw.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Athor:Yuan
 * @Date:2020/2/26$ 15:27$
 * Desc:
 */
@RestController
@RequestMapping("/itemSearch")
public class ItemSearchController {


    @Reference
    private ItemSearchService itemSearchService;

    /**
     * 搜索
     * @param paramMap
     * @return
     */
    @RequestMapping("/search")
    public Result search(@RequestBody Map paramMap){
        try {
            //搜索数据
            Map resulMap = itemSearchService.search(paramMap);
            return new Result(true,"操作成功",resulMap);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"操作失败");
        }
    }
}
