package com.yzw.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yzw.domain.TbGoods;
import com.yzw.entity.Goods;
import com.yzw.entity.Result;
import com.yzw.service.GoodsService;
import com.yzw.utils.FastDFSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 图片上传
 */
@RestController
public class UploadController {
    @Value("${FILE_SERVER_URL}")
    private String path;

    /**
     * 上传
     * @param file
     * @return
     */
    @RequestMapping("/upload")
    public Result upload(@RequestParam("file") MultipartFile file){
        try {
            //初始化fastDFS，加载配置文件
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:config/fdfs_client.conf");
            //获得上传文件的全名
            String originalFilename = file.getOriginalFilename();
            //截取后缀名
            String extName =originalFilename.substring( originalFilename.lastIndexOf(".")+1);
            //文件上传
            String s = fastDFSClient.uploadFile(file.getBytes(), extName);

            //拼接路径
            String url = path+s;
            return new Result(true,"上传成功",url);

        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"上传失败");
        }

    }


}
