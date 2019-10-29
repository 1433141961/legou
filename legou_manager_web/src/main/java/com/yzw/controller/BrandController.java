package com.yzw.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.yzw.domain.Brand;
import com.yzw.entity.Result;
import com.yzw.service.BrandService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * 品牌控制器
 */
// @RestController = @Controller + @ResponseBody
@RestController
// 配置请求路径映射
@RequestMapping("/brand")
public class BrandController {

    // 注入service
    @Reference
    private BrandService brandService;

    /**
     * 查询所有的品牌
     * @return
     */
    @RequestMapping("/findAll")
    public List<Brand> findAll(){
        return brandService.findAll();
    }

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     *
     * /findPage/{pageNum}/{pageSize}：{接收的参数名}  如地址栏为：/findPage/1/10
     *
     * @PathVariable("接收的参数名")：利用注解获得接收的参数
     * 注：在前后端分离的开发情况下我们通常会设置一个结果类Result来接收封装好的数据，以便于前端调用
     * 这是一种restfull 的设计格式
     */

    @RequestMapping("/findPage/{pageNum}/{pageSize}")
    public Result findPage(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize){
        //异常处理，无论前端有没有查询成功都会返回一个数据
        try {
            //分页查询
            PageInfo<Brand> pageInfo = brandService.findPage(pageNum, pageSize);
            return new Result(true,"查询成功",pageInfo.getTotal(),pageInfo.getList());
        } catch (Exception e) {
            e.printStackTrace();
            //查询失败
            return new Result(false,"查询失败");
        }

    }

    /**
     * 保存
     * @param brand
     * @return
     *
     * @requestBody:一般用于json接收的，将接收的json数据转化为实体对象
     */
    @RequestMapping("/save")
    public Result save(@RequestBody Brand brand) {

        try {
            //调用新增方法
            brandService.save(brand);
            //保存成功返回结果给页面
            return new Result(true, "保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "保存失败");
        }
    }

    /**
     * 根据id查询品牌信息
     * @return
     */
    @RequestMapping("/findOne/{id}")
    public Result findOne(@PathVariable("id") Long id){
        try {
            Brand brand =  brandService.findOne(id);
            return new Result(true,"查询成功",brand);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"查询失败");
        }
    }
    /**
     * 修改拍信息
     * @return
     */
    @RequestMapping("/update")
    public Result update(@RequestBody Brand brand){
        try {
            brandService.update(brand);
            return new Result(true,"修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"修改失败");
        }
    }
    /**
     * 删除
     * @return
     */
    @RequestMapping("/delete")
    public Result delete(Long[] ids){
        try {
            brandService.delete(ids);
            return new Result(true,"删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"删除失败");
        }
    }


}
