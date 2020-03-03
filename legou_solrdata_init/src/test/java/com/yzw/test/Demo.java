package com.yzw.test;

import com.yzw.domain.TbItem;
import com.yzw.domain.TbItemExample;
import com.yzw.mapper.TbItemMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.SolrDataQuery;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @Athor:Yuan
 * @Date:2020/2/23$ 13:12$
 * Desc:测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
//这里需要把applicationContext-dao.xml引入
@ContextConfiguration("classpath*:spring/applicationContext*.xml")
public class Demo {

    @Autowired
    private SolrTemplate solrTemplate;

    @Autowired
    private TbItemMapper tbItemMapper;

    @Test
    public void delete(){
        //查询所有
        SolrDataQuery solrDataQuery = new SimpleQuery("*:*");
        solrTemplate.delete(solrDataQuery);
        solrTemplate.commit();
    }

     @Test
    public void addByDB(){
         //查询到item下的所有数据
         List<TbItem> tbItems = tbItemMapper.selectByExample(null);
         //保存所有
         solrTemplate.saveBeans(tbItems);
         //提交事务
         solrTemplate.commit();

     }


}
