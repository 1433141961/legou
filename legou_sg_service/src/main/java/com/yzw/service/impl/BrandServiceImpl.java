package com.yzw.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.yzw.domain.Brand;
import com.yzw.mapper.BrandMapper;
import com.yzw.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
//开启事务
@Transactional
//开启dubbo服务,是alibaba的不是spring的包
@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandMapper brandMapper;

    @Override
    public List<Brand> findAll() {
        List<Brand> brandList = brandMapper.findAll();
        return brandList;
    }
}
