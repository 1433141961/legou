package com.yzw.service;


import com.github.pagehelper.PageInfo;
import com.yzw.domain.TbSeller;

import java.util.List;

/**
 * 服务层接口
 */
public interface SellerService {

    /**
     * 返回全部列表
     *
     * @return
     */
    public List<TbSeller> findAll();

    /**
     * 返回分页列表
     *
     * @return
     */
    public PageInfo<TbSeller> findPage(int pageNum, int pageSize);

    /**
     * 增加
     */
    public void add(TbSeller seller);


    /**
     * 修改
     */
    public void update(TbSeller seller);


    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    public TbSeller findOne(String id);


    /**
     * 批量删除
     *
     * @param ids
     */
    public void delete(String[] ids);


    /**
     * 分页
     *
     * @param pageNum  当前页 码
     * @param pageSize 每页记录数
     * @return
     */
    public PageInfo<TbSeller> findPage(TbSeller seller, int pageNum, int pageSize);


    void auditing(String sellerId, String status);
}
