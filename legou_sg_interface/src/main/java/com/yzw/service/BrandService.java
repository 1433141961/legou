package com.yzw.service;

import com.github.pagehelper.PageInfo;
import com.yzw.domain.Brand;

import java.util.List;

/**
 * 品牌服务接口
 */
public interface BrandService{
    //查询所有
    public List<Brand> findAll();
    //分页查询
    public PageInfo<Brand> findPage(int pageNum, int pageSize);
    //新增
    void save(Brand brand);

    Brand findOne(Long id);

    void update(Brand brand);

    void delete(Long[] ids);
}


