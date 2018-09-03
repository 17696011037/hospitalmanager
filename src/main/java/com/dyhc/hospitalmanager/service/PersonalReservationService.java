package com.dyhc.hospitalmanager.service;

import com.dyhc.hospitalmanager.pojo.Check;
import com.dyhc.hospitalmanager.pojo.Combination;
import com.dyhc.hospitalmanager.pojo.Package;
import com.dyhc.hospitalmanager.pojo.PersonInfo;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

/**
 * 个人预约业务
 */
public interface PersonalReservationService {

    /**
     * 根据身份证号查询用户信息表
     * @param personIdCard
     * @return
     */
    PersonInfo getPersonInfoByNameAndCard(String personIdCard);

    /**
     * 用户预约
     * @param personInfo 用户信息
     * @param Yudate 预约时间
     * @return 1成功
     *          -1失败
     *          -2添加用户信息失败
     *          -3添加预约表失败
     */
    String UserReservation(PersonInfo personInfo,String Yudate,Integer[] packId, Integer[] comId, Integer[] checkId);

    /**
     * 获取redis中的所有键值
     * @return
     */
    Object listDate();

    String userReservation(PersonInfo personInfo,String Yudate,Integer[] packId, Integer[] comId, Integer[] checkId);

    /**
     * 获取所有的检查项
     * @return
     */
    List<Check> getAllCheck();

    /**
     * 获取所有组合项
     * @return
     */
    List<Combination> getAllCombination();

    /**
     * 获取所有套餐
     * @return
     */
    List<com.dyhc.hospitalmanager.pojo.Package> getPackages();

    /**
     * 获取该组合项下的所有体检项
     * @param comId 组合项Id
     * @return
     */
    List<Check> getComCheck(Integer comId);

    /**
     * 获取该套餐项下的所有体检项
     * @param packId 套餐id
     * @return
     */
    Package getPackCheck(Integer packId);
}
