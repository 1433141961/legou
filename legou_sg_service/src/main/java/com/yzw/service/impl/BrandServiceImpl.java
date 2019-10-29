package com.yzw.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yzw.domain.Brand;
import com.yzw.mapper.BrandMapper;
import com.yzw.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 品牌服务接口实现类
 */
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

    @Override
    public PageInfo<Brand> findPage(int pageNum, int pageSize) {
        //调用分页方法，设置参数
        PageHelper.startPage(pageNum,pageSize);

        // 利用mybatis的分页插件可以不用写分页limit,直接查询所有即可，插件为我们自动封装好啦方法
        List<Brand> brandList =  brandMapper.findAll();
        //进行数据封装
        PageInfo<Brand> pageInfo = new PageInfo<>(brandList);

       return pageInfo;
    }

    @Override
    public void save(Brand brand) {
        brandMapper.save(brand);
    }


    @Override
    public void update(Brand brand) {
        brandMapper.update(brand);
    }

    @Override
    public void delete(Long[] ids) {
        for (long id : ids){
            brandMapper.delete(id);
        }
    }

    @Override
    public Brand findOne(Long id) {

        return brandMapper.findOne(id);
    }
}
