package com.dyhc.hospitalmanager.dao;

import com.dyhc.hospitalmanager.pojo.CompanyInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CompanyInfoMapper {
    /**
     * 李文荣
     *  根据单位名称查询单位表
     * @param companyName 单位名称
     * @return
     */
    CompanyInfo showCompanyInfo(@Param("companyName") String companyName);

    /**
     * 李文荣
     * 修改单位信息
     * @param companyInfo
     * @return
     */
    Integer updCompanyInfo(CompanyInfo companyInfo);

    /**
     * 李文荣
     * 添加单位信息
     * @param companyInfo
     * @return
     */
    Integer addCompanyInfo(CompanyInfo companyInfo);
}