package com.yzw.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yzw.domain.TbSeller;
import com.yzw.domain.TbSellerExample;
import com.yzw.mapper.TbSellerMapper;
import com.yzw.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 服务实现层
 *
 * @author Administrator
 */
@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private TbSellerMapper sellerMapper;

    /**
     * 查询所有
     *
     * @return
     */
    @Override
    public List<TbSeller> findAll() {
        return sellerMapper.selectByExample(null);
    }

    /**
     * 分页查询
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<TbSeller> findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<TbSeller> list = sellerMapper.selectByExample(null);
        return new PageInfo<>(list);
    }

    /**
     * 保存
     *
     * @param seller
     */
    @Override
    public void add(TbSeller seller) {
        sellerMapper.insert(seller);
    }

    /**
     * 修改
     *
     * @param seller
     */
    @Override
    public void update(TbSeller seller) {
        sellerMapper.updateByPrimaryKey(seller);
    }

    /**
     * 通过主键查询
     *
     * @param id
     * @return
     */
    @Override
    public TbSeller findOne(String id) {
        return sellerMapper.selectByPrimaryKey(id);
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(String[] ids) {
        for (String id : ids) {
            sellerMapper.deleteByPrimaryKey(id);
        }
    }

    /**
     * 分页条件查询
     *
     * @param seller
     * @param pageNum  当前页 码
     * @param pageSize 每页记录数
     * @return
     */
    @Override
    public PageInfo<TbSeller> findPage(TbSeller seller, int pageNum, int pageSize) {
        //设置分页
        PageHelper.startPage(pageNum,pageSize);
        //创建查询条件对象
        TbSellerExample example = new TbSellerExample();
        TbSellerExample.Criteria criteria = example.createCriteria();
        //获得状态
        String status = seller.getStatus();
        //设置条件
        if (status!=null && !status.trim().isEmpty()){
            criteria.andStatusEqualTo(status);
        }
        // 查询数据
        List<TbSeller> list = sellerMapper.selectByExample(example);
        // 数据封装
        return new PageInfo<>(list);
    }

    /**
     * 审核商家
     * @param sellerId
     * @param status
     */
    @Override
    public void auditing(String sellerId, String status) {
        // 通过商家登录名称，查询商家对象
        TbSeller tbSeller = sellerMapper.selectByPrimaryKey(sellerId);
        // 设置状态
        tbSeller.setStatus(status);
        // 更新商家的数据
        sellerMapper.updateByPrimaryKey(tbSeller);
    }

}
