package com.yzw.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yzw.domain.Brand;
import com.yzw.service.BrandService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
