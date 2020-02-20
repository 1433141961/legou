package com.yzw.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yzw.domain.TbSpecificationOption;
import com.yzw.domain.TbSpecificationOptionExample;
import com.yzw.domain.TbTypeTemplate;
import com.yzw.domain.TbTypeTemplateExample;
import com.yzw.mapper.TbSpecificationOptionMapper;
import com.yzw.mapper.TbTypeTemplateMapper;
import com.yzw.service.TypeTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 *
 * 模板实现类
 */
@Service
@Transactional
public class TypeTemplateServiceImpl implements TypeTemplateService {

    @Autowired
    private TbTypeTemplateMapper tbTypeTemplateMapper;
    @Autowired
    private TbSpecificationOptionMapper tbSpecificationOptionMapper;

    /**
     * 查询所有
     * @return
     */
    @Override
    public List<TbTypeTemplate> findAll() {
        return tbTypeTemplateMapper.selectByExample(null);
    }

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<TbTypeTemplate> findPage(int pageNum, int pageSize) {
        // 设置当前页和每页条数
        PageHelper.startPage(pageNum,pageSize);
        // 查询所有
        List<TbTypeTemplate> list = tbTypeTemplateMapper.selectByExample(null);
        // 封装数据
        PageInfo<TbTypeTemplate> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 添加
     * @param typeTemplate
     */
    @Override
    public void add(TbTypeTemplate typeTemplate) {
        tbTypeTemplateMapper.insert(typeTemplate);
    }

    /**
     * 修改
     * @param typeTemplate
     */
    @Override
    public void update(TbTypeTemplate typeTemplate) {
        tbTypeTemplateMapper.updateByPrimaryKey(typeTemplate);
    }

    /**
     * 通过主键查询
     * @param id
     * @return
     */
    @Override
    public TbTypeTemplate findOne(Long id) {
        return tbTypeTemplateMapper.selectByPrimaryKey(id);
    }

    /**
     * 批量删除
     * @param ids
     */
    @Override
    public void delete(Long[] ids) {
        // 遍历ids 删除
        for (Long id : ids) {
            // 删除
            tbTypeTemplateMapper.deleteByPrimaryKey(id);
        }
    }

    /**
     * 条件查询
     * @param typeTemplate
     * @param pageNum 当前页 码
     * @param pageSize 每页记录数
     * @return
     */
    @Override
    public PageInfo<TbTypeTemplate> findPage(TbTypeTemplate typeTemplate, int pageNum, int pageSize) {
        // 设置当前页和每页条数
        PageHelper.startPage(pageNum, pageSize);

        // 拼接查询条件
        TbTypeTemplateExample example=new TbTypeTemplateExample();
        TbTypeTemplateExample.Criteria criteria = example.createCriteria();

        if(typeTemplate!=null){
            if(typeTemplate.getName()!=null && typeTemplate.getName().length()>0){
                criteria.andNameLike("%"+typeTemplate.getName()+"%");
            }
            if(typeTemplate.getSpecIds()!=null && typeTemplate.getSpecIds().length()>0){
                criteria.andSpecIdsLike("%"+typeTemplate.getSpecIds()+"%");
            }
            if(typeTemplate.getBrandIds()!=null && typeTemplate.getBrandIds().length()>0){
                criteria.andBrandIdsLike("%"+typeTemplate.getBrandIds()+"%");
            }
            if(typeTemplate.getCustomAttributeItems()!=null && typeTemplate.getCustomAttributeItems().length()>0){
                criteria.andCustomAttributeItemsLike("%"+typeTemplate.getCustomAttributeItems()+"%");
            }
        }

        // 查询所有
        List<TbTypeTemplate> list = tbTypeTemplateMapper.selectByExample(example);
        // 封装数据
        PageInfo<TbTypeTemplate> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 查询规格信息
     * @param id 模板id
     * @return List<Map></>map：有三组数据：规格id ，规格text，规格项
     */
    @Transactional
    @Override
    public List<Map> findSpecList(Long id) {

        //首先根据模板id拿到模板对象
        TbTypeTemplate tbTypeTemplate = tbTypeTemplateMapper.selectByPrimaryKey(id);
        //获得模板下的规格id字符串[{"id":27,"text":"网络"},{"id":32,"text":"机身内存"}]
        String specIds = tbTypeTemplate.getSpecIds();
        //转化为json,并返回存map的list集合
        List<Map> list = JSON.parseArray(specIds,Map.class);
        for (Map map : list) {
            //获得规格id
           long specId = Long.parseLong( map.get("id")+"");
           //查询条件
            TbSpecificationOptionExample example = new TbSpecificationOptionExample();

            TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
            criteria.andSpecIdEqualTo(specId);
            //查询规格项
            List<TbSpecificationOption> tbSpecificationOptions = tbSpecificationOptionMapper.selectByExample(example);

            //将规格项集合保存到集合中
            map.put("options",tbSpecificationOptions);
        }
        return list;
    }

}
