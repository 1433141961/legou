package com.yzw.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.yzw.domain.Brand;
import com.yzw.domain.TbSpecification;
import com.yzw.entity.Result;
import com.yzw.entity.Specification;
import com.yzw.service.SpecificationService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/specification")
public class SpecificationController {

    @Reference
    private SpecificationService specificationService;

    /**
     * 分页查询，采用restfull方式传递参数
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/findPage/{pageNum}/{pageSize}")
    public Result findPage(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize) {
        try {
            PageInfo<TbSpecification> pageInfo = specificationService.findPage(pageNum, pageSize);

            return new Result(true, "查询成功", pageInfo.getTotal(), pageInfo.getList());
        } catch (Exception e) {
            e.printStackTrace();
            //查询失败返回
            return new Result(false, "查询成功");

        }

    }
    @RequestMapping("/save")
    public Result save(@RequestBody Specification specification){
        try {
            specificationService.save(specification);
            return new Result(true,"保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"保存失败");

        }
    }

    /**
     * 根据规格id查询规格和规格选项信息
     * @param id
     * @return
     */
    @RequestMapping("/findOne/{id}")
    public Result findOne(@PathVariable("id") long id){
        try {
            Specification specification = specificationService.findOne(id);
            return new Result(true,"操作成功",specification);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"操作失败");

        }
    }
    @RequestMapping("/update")
    public Result update(@RequestBody Specification specification){
        try {
            specificationService.update(specification);
            return new Result(true,"操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"操作失败");

        }
    }

    /**
     * 删除规格
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    public Result delete(long[] ids){
        try {
            specificationService.delete(ids);
            return new Result(true,"删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"删除失败");

        }
    }
    /**
     * 查询规格集，用于模板模块新增功能中select2选项框
     * @return
     */
    @RequestMapping("/findSpecificationList")
    public Result findSpecificationList(){
        try {
            List<TbSpecification> specificationList = specificationService.findAll();
            //封装为我们需要的数据
            List<Map> specMapList = new ArrayList<>();
            for (TbSpecification tbSpecification : specificationList){
                Map<String,Object> map = new HashMap<>();
                map.put("id",tbSpecification.getId());
                map.put("text",tbSpecification.getSpecName());
                //添加到集合
                specMapList.add(map);
            }
            return new Result(true,"操作成功",specMapList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"操作失败");

        }
    }


}
