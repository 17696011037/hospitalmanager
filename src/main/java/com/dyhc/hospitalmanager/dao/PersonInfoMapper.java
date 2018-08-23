package com.dyhc.hospitalmanager.dao;

import com.dyhc.hospitalmanager.pojo.PersonInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PersonInfoMapper {
    /**
     *  李文荣
     *  根据姓名和身份证查询用户信息
     * @param personName 用户姓名
     * @param personIdCard 身份证号
     * @return
     */
    List<PersonInfo> findPersonInfos(@Param("personName") String personName, @Param("personIdCard") String personIdCard)throws  Exception;

    /**
     * 李文荣
     * 添加用户信息
     * @param personInfo 用户信息
     * @return
     */
    Integer addPersonInfo(PersonInfo personInfo)throws  Exception;

    /**
     * crf
     * 根据手机号查询人员信息
     * @param personTelephone 手机号属性
     * @return 返回一个人员信息对象
     */
    PersonInfo getPersonInfoByPersonTelephone(@Param("personTelephone")String personTelephone)throws  Exception;

    /**
     * 周冰洋
     * 修改用户
     * @param personInfo 用户信息
     * @return
     */
    Integer updPersonInfo(PersonInfo personInfo)throws  Exception;

    /**
     * 李文荣
     *  根据用户身份证号码查询用户信息
     * @param personIdCard 身份证号码
     * @return
     */
    PersonInfo findPersonInfoPersonIdCard(@Param("personIdCard") String personIdCard)throws  Exception;

    /**
     * crf
     * 根据人员id查询人员信息
     * @param personId
     * @return
     */
    PersonInfo getPersonInfoByPersonId(@Param("personId")Integer personId)throws  Exception;

}
