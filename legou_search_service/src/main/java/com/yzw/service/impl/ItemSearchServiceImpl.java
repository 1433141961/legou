package com.yzw.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.yzw.domain.TbItem;
import com.yzw.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.GroupEntry;
import org.springframework.data.solr.core.query.result.GroupPage;
import org.springframework.data.solr.core.query.result.GroupResult;
import org.springframework.data.solr.core.query.result.ScoredPage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Athor:Yuan
 * @Date:2020/2/26$ 16:03$
 * Desc:shangp搜索服务类
 */
@Service
public class ItemSearchServiceImpl implements ItemSearchService {

    @Autowired
    private SolrTemplate solrTemplate;
    /**
     * 搜索
     * @param paramMap
     * @return
     */
    @Override
    public Map search(Map paramMap) {
        //创建map
        Map resultMap = new HashMap();
        //获得查询条件
        String keyWord =(String) paramMap.get("keyword");
        //创建查询对象
        Query query = new SimpleQuery();
        //穿件查询条件
        Criteria criteria =new Criteria("item_keywords").is(keyWord);
        //添加查询条件
        query.addCriteria(criteria);
        //获得分类
        String category = (String) paramMap.get("category");
        //=========类别过滤===========
        if (category != null && !category.isEmpty()){
            //创建过滤查询对象
            FilterQuery filterQuery = new SimpleFilterQuery();
            //添加过滤查询
            filterQuery.addCriteria(new Criteria("item_category").is(category));
            query.addFilterQuery(filterQuery);
        }

        //获得品牌
        String brand = (String) paramMap.get("brand");
        //=========品牌过滤===========
        if (brand != null && !brand.isEmpty()){
            //创建过滤查询对象
            FilterQuery filterQuery = new SimpleFilterQuery();
            //添加过滤查询
            filterQuery.addCriteria(new Criteria("item_brand").is(brand));
            query.addFilterQuery(filterQuery);
        }
        // 设置分页查询
        // 获取当前页
        int page = (int) paramMap.get("page");
        // 设置起始位置
        query.setOffset((page-1)*60);
        // 设置每页查询的60条数
        query.setRows(60);
        //根据关键字分页
        ScoredPage<TbItem> tbItems = solrTemplate.queryForPage(query, TbItem.class);
        // 设置总记录数
        resultMap.put("total",tbItems.getTotalElements());
        // 设置总页数
        resultMap.put("totalPages",tbItems.getTotalPages());
//        封装数据
        resultMap.put("rows",tbItems.getContent());

        //=====================分组查询，获得分类信息===========================
        //// 需求：select item_category from tb_item  item_keywords = '华为' group by item_category

        //主条件查询
        //创建查询对象
        Query groupQuery = new SimpleQuery();
        //添加查询条件
        groupQuery.addCriteria(new Criteria("item_keywords").is(keyWord));
        //创建分组条件
        GroupOptions groupOptions = new GroupOptions();
        groupOptions.addGroupByField("item_category");
        //设置分组条件
        groupQuery.setGroupOptions(groupOptions);

        //执行分组查询
        GroupPage<TbItem> groupPageCategory = solrTemplate.queryForGroupPage(groupQuery, TbItem.class);

        // 下面solrTempate封装的一段API

        //封装分组结果
        List<String> groupValueList = new ArrayList<>();
        //获得分组结果的item对象
        GroupResult<TbItem> item_category = groupPageCategory.getGroupResult("item_category");
        //获得分组实体
        Page<GroupEntry<TbItem>> groupEntries = item_category.getGroupEntries();
        List<GroupEntry<TbItem>> content = groupEntries.getContent();
        for (GroupEntry<TbItem> tbItemGroupEntry : content) {
            String groupValue = tbItemGroupEntry.getGroupValue();
            groupValueList.add(groupValue);
        }
        //封装类别数据
        resultMap.put("categoryList",groupValueList);

        //======================分组查询品牌=================================
        //逻辑和上面额类别分组一样

        // 主查询条件
        // 创建查询条件对象
        Query groupQueryBrand = new SimpleQuery();
        // 添加查询的条件
        groupQueryBrand.addCriteria(new Criteria("item_keywords").is(keyWord));

        // 创建分组
        GroupOptions groupOptionsBrand = new GroupOptions();
        groupOptionsBrand.addGroupByField("item_brand");
        // 设置分组的查询
        groupQueryBrand.setGroupOptions(groupOptionsBrand);
        // 执行查询
        GroupPage<TbItem> groupPageBrand = solrTemplate.queryForGroupPage(groupQueryBrand, TbItem.class);

        // 创建List集合，存储查询的组的名称
        List<String> brandList = new ArrayList<>();

        // 下面solrTempate封装的一段API
        GroupResult<TbItem> item_brand = groupPageBrand.getGroupResult("item_brand");
        Page<GroupEntry<TbItem>> groupEntriesBrand = item_brand.getGroupEntries();
        for (GroupEntry<TbItem> tbItemGroupEntry : groupEntriesBrand.getContent()) {
            String v = tbItemGroupEntry.getGroupValue();
            brandList.add(v);
        }


        resultMap.put("brandList",brandList);
        return resultMap;
    }
}
