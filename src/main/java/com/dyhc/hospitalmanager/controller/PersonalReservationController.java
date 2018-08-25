package com.dyhc.hospitalmanager.controller;

import com.dyhc.hospitalmanager.pojo.Check;
import com.dyhc.hospitalmanager.pojo.Combination;
import com.dyhc.hospitalmanager.pojo.Package;
import com.dyhc.hospitalmanager.pojo.PersonInfo;
import com.dyhc.hospitalmanager.service.PersonalReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

/**
 * 个人预约
 * zby
 */
public class PersonalReservationController {

    @Autowired
    private PersonalReservationService personalReservation;

    /**
     * 根据身份证号查询用户信息表，绑定信息框
     * @param personIdCard 身份证号
     * @return
     */
    @GetMapping("/getPersonInfoByNameAndCard.do")
    public PersonInfo getPersonInfoByNameAndCard(@RequestParam("personIdCard") String personIdCard){
        return personalReservation.getPersonInfoByNameAndCard(personIdCard);
    }

    /**
     * 用户预约
     * @param personInfo 用户信息
     * @param Yudate 预约时间
     * @return 1成功
     *          -1失败
     *          -2添加用户信息失败
     *          -3添加预约表失败
     */
    @PostMapping("/UserReservation.do")
    public int UserReservation(@RequestParam("personInfo") PersonInfo personInfo,@RequestParam("Yudate") Date Yudate){
        return personalReservation.UserReservation(personInfo,Yudate);
    }

    /**
     * 获取所有的检查项
     * @return
     */
    @GetMapping("/ExhibitionAllCheck.do")
    public List<Check> getAllCheck(){
        return personalReservation.getAllCheck();
    }

    /**
     * 获取所有组合项
     * @return
     */
    @GetMapping("/ExhibitionAllCombination.do")
    public List<Combination> getAllCombination(){
        return personalReservation.getAllCombination();
    }

    /**
     * 获取所有套餐
     * @return
     */
    @GetMapping("/ExhibitionAllPackages")
    public List<Package> getPackages(){
        return personalReservation.getPackages();
    }

    /**
     * 新增用户选择的套餐、组合项、体检项
     * @param physicalExaminationId 体检编号
     * @param packId 所选的套餐项
     * @param comId 所选的组合项
     * @param checkId 所选的体检项
     * @return null
     */
    @PostMapping("/")
    public Integer addPersonCheck(@RequestParam("physicalExaminationId") String physicalExaminationId,
                                  @RequestParam("packId[]") Integer[] packId,
                                  @RequestParam("comId[]") Integer[] comId,
                                  @RequestParam("checkId[]") Integer[] checkId){
        return personalReservation.addPersonCheck(physicalExaminationId,packId,comId,checkId);
    }
}
