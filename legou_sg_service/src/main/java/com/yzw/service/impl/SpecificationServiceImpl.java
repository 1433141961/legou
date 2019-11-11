package com.yzw.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yzw.domain.TbSpecification;
import com.yzw.domain.TbSpecificationOption;
import com.yzw.domain.TbSpecificationOptionExample;
import com.yzw.entity.Specification;
import com.yzw.mapper.TbSpecificationMapper;
import com.yzw.mapper.TbSpecificationOptionMapper;
import com.yzw.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * 规格模块服务实现类
 */
@Service
public class SpecificationServiceImpl implements SpecificationService {
    @Autowired
    TbSpecificationMapper specificationMapper;

    @Autowired
    TbSpecificationOptionMapper specificationOptionMapper;

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<TbSpecification> findPage(int pageNum, int pageSize) {

        //开启分页查询
        PageHelper.startPage(pageNum,pageSize);
        //mybatis分页查询不需要我们使用limit查询，直接查询所有即可
        List<TbSpecification> tbSpecificationList = specificationMapper.findAll();

        PageInfo<TbSpecification> pageInfo = new PageInfo<>(tbSpecificationList);

        return pageInfo;
    }

    /**
     * 查询所有规格
     * @return
     */
    @Override
    public List<TbSpecification> findAll() {

        return specificationMapper.findAll();
    }

    /**
     * 删除规格和规格项
     * @param ids
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void delete(long[] ids) {
        for (long id : ids){
            specificationMapper.delete(id);
            specificationOptionMapper.deleBySpecId(id);
        }

    }

    /**
     * 插入规格和规格项
     * @param specification
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void save(Specification specification) {
        TbSpecification tbSpecification = specification.getTbSpecification();
        //首先先保存规格
        specificationMapper.insert(tbSpecification);
        //获得规格项
        List<TbSpecificationOption> specificationOptionList = specification.getTbSpecificationOptionList();
        //获得规格主键
        Long id = tbSpecification.getId();

        //其次再保存规格项
        for(TbSpecificationOption specificationOption : specificationOptionList){
            //由于没有设置表之间的约束，需要手动在业务中实现这种约束
            specificationOption.setSpecId(id);
            specificationOptionMapper.insert(specificationOption);
        }

    }


    /**
     * 根据规格选id查询规格和规格信息
     * @param id
     * @return
     */
    @Override
    public Specification findOne(long id) {
        //查询规格
        TbSpecification tbSpecification = specificationMapper.selectByPrimaryKey(id);
        //查询规格项
        List<TbSpecificationOption> specificationOptionList= specificationOptionMapper.findBySepecId(id);
        //设置组装类
        Specification specification = new Specification();
        specification.setTbSpecification(tbSpecification);
        specification.setTbSpecificationOptionList(specificationOptionList);

        return specification;
    }

    /**
     * 修改，（这里由于我们无法知道修改的规格选项的条数所以采用先删除掉规格信息然后再重新插入规格选项信息）
     * @param specification
     */
    @Override
    public void update(Specification specification) {

        TbSpecification tbSpecification = specification.getTbSpecification();
        //修改规格
        specificationMapper.updateByPrimaryKey(tbSpecification);
        //===========首先需要删除旧的规格选项信息================
        Long id = specification.getTbSpecification().getId();
        //删除规格选项
        specificationOptionMapper.deleBySpecId(id);

        //=============然后再插入规格选项=================
        //获得规格选项
        List<TbSpecificationOption> tbSpecificationOptionList = specification.getTbSpecificationOptionList();

        for (TbSpecificationOption tbSpecificationOption : tbSpecificationOptionList){
            //设置规格id
            tbSpecificationOption.setSpecId(id);
            //保存规格选项
            specificationOptionMapper.insert(tbSpecificationOption);
        }
    }

}
