package com.yzw.mapper;

import com.yzw.domain.TbSpecificationOption;
import com.yzw.domain.TbSpecificationOptionExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbSpecificationOptionMapper {
    int countByExample(TbSpecificationOptionExample example);

    int deleteByExample(TbSpecificationOptionExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbSpecificationOption record);

    int insertSelective(TbSpecificationOption record);

    List<TbSpecificationOption> selectByExample(TbSpecificationOptionExample example);

    TbSpecificationOption selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbSpecificationOption record, @Param("example") TbSpecificationOptionExample example);

    int updateByExample(@Param("record") TbSpecificationOption record, @Param("example") TbSpecificationOptionExample example);

    int updateByPrimaryKeySelective(TbSpecificationOption record);

    int updateByPrimaryKey(TbSpecificationOption record);
    //根据规格id查询规格选项
    List<TbSpecificationOption> findBySepecId(long id);
    //根据规格id删除规格选项信息
    void deleBySpecId(Long id);

    void updateBySpecId(TbSpecificationOption tbSpecificationOption);
}