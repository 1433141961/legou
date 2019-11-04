package com.yzw.entity;

import com.yzw.domain.TbSpecification;
import com.yzw.domain.TbSpecificationOption;

import java.io.Serializable;
import java.util.List;

/**
 * 规格和规格选项的组合类
 */
public class Specification implements Serializable{
    private TbSpecification tbSpecification;

    private List<TbSpecificationOption> tbSpecificationOptionList;

    public TbSpecification getTbSpecification() {
        return tbSpecification;
    }

    public void setTbSpecification(TbSpecification tbSpecification) {
        this.tbSpecification = tbSpecification;
    }

    public List<TbSpecificationOption> getTbSpecificationOptionList() {
        return tbSpecificationOptionList;
    }

    public void setTbSpecificationOptionList(List<TbSpecificationOption> tbSpecificationOptionList) {
        this.tbSpecificationOptionList = tbSpecificationOptionList;
    }
}
