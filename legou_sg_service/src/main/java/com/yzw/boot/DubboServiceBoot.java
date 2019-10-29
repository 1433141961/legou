package com.yzw.boot;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * 启动类
 */

public class DubboServiceBoot {
        public static void main(String[] args) throws IOException {
            // 加载
            // classpath*   加载当前项目中所有配置文件，还会去加载jar包中所有的配置文件
            // :spring/applicationContext*.xml
            ApplicationContext ac = new ClassPathXmlApplicationContext("classpath*:spring/applicationContext*.xml");
            // 阻塞
            System.in.read();
        }
    }
