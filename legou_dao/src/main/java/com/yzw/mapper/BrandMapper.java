package com.yzw.mapper;

import com.yzw.domain.Brand;

import java.util.List;

/**
 * 品牌dao接口
 */
public interface BrandMapper {
    //查询所有
    public List<Brand> findAll();
    //新增
    void save(Brand brand);
    //根据id查询
    Brand findOne(Long id);
    //更新
    void update(Brand brand);

    void delete(long id);
}
