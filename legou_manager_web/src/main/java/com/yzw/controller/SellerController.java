package com.yzw.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.yzw.domain.TbSeller;
import com.yzw.entity.Result;
import com.yzw.service.SellerService;
import org.springframework.security.access.method.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * controller
 */
@RestController
@RequestMapping("/seller")
public class SellerController {

    @Reference
    private SellerService sellerService;

    /**
     * 返回全部列表
     *
     * @return
     */
    @RequestMapping("/findAll")
    public Result findAll() {
        try {
            List<TbSeller> list = sellerService.findAll();
            return new Result(true, "查询成功", list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "查询失败");
        }
    }

    /**
     * 返回全部列表
     *
     * @return
     */
    @RequestMapping("/findPage/{pageNum}/{pageSize}")
    public Result findPage(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize) {
        try {
            PageInfo<TbSeller> page = sellerService.findPage(pageNum, pageSize);
            return new Result(true, "查询成功", page.getTotal(), page.getList());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "查询失败");
        }
    }

    /**
     * 入住申请
     *
     * @param seller
     * @return
     */
    @RequestMapping("/save")
    public Result save(@RequestBody TbSeller seller) {
        try {
            // 对密码进行加密的处理
            // 加密的方式
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            // 对密码加密 设置进去
            seller.setPassword(encoder.encode(seller.getPassword()));
            // 判断密码是否正确，使用框架提供方法
            // encoder.matches("123456","密文的密码");

            // 商家的状态  未审核的状态
            seller.setStatus("0");

            // 保存
            sellerService.add(seller);
            return new Result(true, "增加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "增加失败");
        }
    }

    /**
     * 修改
     *
     * @param seller
     * @return
     */
    @RequestMapping("/update")
    public Result update(@RequestBody TbSeller seller) {
        try {
            sellerService.update(seller);
            return new Result(true, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改失败");
        }
    }

    /**
     * 获取实体
     *
     * @return
     */
    @RequestMapping("/findOne/{id}")
    public Result findOne(@PathVariable("id") String id) {
        try {
            TbSeller seller = sellerService.findOne(id);
            return new Result(true, "查询成功",seller);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "查询失败");
        }
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @RequestMapping("/delete/{ids}")
    public Result delete(@PathVariable("ids") String[] ids) {
        try {
            sellerService.delete(ids);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败");
        }
    }

    /**
     * 查询+分页
     *
     * @param
     * @param
     * @param
     * @return
     */
    @RequestMapping("/search/{pageNum}/{pageSize}")
    public Result search(@RequestBody TbSeller seller, @PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize) {
        try {
            PageInfo<TbSeller> page = sellerService.findPage(seller, pageNum, pageSize);
            return new Result(true, "查询成功", page.getTotal(), page.getList());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "查询失败");
        }
    }

    /**
     *商家审核
     * @param sellerId
     * @param status
     * @return
     */
    @RequestMapping("/auditing/{sellerId}/{status}")
    public Result auditing(@PathVariable("sellerId") String sellerId, @PathVariable("status") String status) {
        try {
            sellerService.auditing(sellerId,status);
            return new Result(true, "审核成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "审核失败");
        }
    }



}
