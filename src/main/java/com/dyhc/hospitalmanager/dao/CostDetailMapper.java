package com.dyhc.hospitalmanager.dao;

import com.dyhc.hospitalmanager.pojo.CostDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CostDetailMapper {

    /**
     * 添加费用明细表
     * @param costDetail 费用明细对象
     * @return 返回一个处理结果
     */
    Integer addCostDetail(@Param("costDetail")CostDetail costDetail)throws Exception;

}
