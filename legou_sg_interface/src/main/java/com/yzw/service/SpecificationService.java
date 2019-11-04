package com.yzw.service;

import com.github.pagehelper.PageInfo;
import com.yzw.domain.TbSpecification;
import com.yzw.entity.Specification;

import java.util.List;

/**
 * 规格模块接口
 */
public interface SpecificationService {
    //分页查询
    PageInfo<TbSpecification> findPage(int pageNum, int pageSize);
    //查询所有
    List<TbSpecification> findAll();

    void delete(long[] ids);

    void save(Specification specification);

    Specification findOne(long id);

    void update(Specification specification);
}
