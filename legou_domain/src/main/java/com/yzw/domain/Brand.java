package com.yzw.domain;

import java.io.Serializable;

/**
 * 品牌
 */

public class Brand implements Serializable {

    // 主键
    private Long id;
    // 品牌名称
    private String name;
    // mybatis框架有配置，在实体类中写驼峰命名方式，对应上字段下划线方式
    // firstChar = first_char
    // 首字母
    private String firstChar;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstChar() {
        return firstChar;
    }

    public void setFirstChar(String firstChar) {
        this.firstChar = firstChar;
    }
}
