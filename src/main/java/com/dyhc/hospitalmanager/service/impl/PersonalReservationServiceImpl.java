package com.dyhc.hospitalmanager.service.impl;

import com.dyhc.hospitalmanager.dao.*;
import com.dyhc.hospitalmanager.pojo.Check;
import com.dyhc.hospitalmanager.pojo.Combination;
import com.dyhc.hospitalmanager.pojo.Package;
import com.dyhc.hospitalmanager.pojo.PersonInfo;
import com.dyhc.hospitalmanager.pojo.PhysicalExamination;
import com.dyhc.hospitalmanager.service.PersonalReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PersonalReservationServiceImpl implements PersonalReservationService {

    private Logger logger = LoggerFactory.getLogger(PersonalReservationServiceImpl.class);;

    //人员信息表Mapper
    @Autowired
    private PersonInfoMapper personInfoMapper;
    //体检表Mapper
    @Autowired
    private PhysicalExaminationMapper physicalExaminationMapper;
    //体检项Mapper
    @Autowired
    private CheckMapper checkMapper;
    //组合项Mapper
    @Autowired
    private CombinationMapper combinationMapper;
    //套餐Mapper
    @Autowired
    private PackageMapper packageMapper;
    //用户和套餐项的关系Mapper
    @Autowired
    private PhysicalExaminationAndPackageMapper physicalExaminationAndPackageMapper;
    //用户和组合项的关系Mapper
    @Autowired
    private PhysicalExaminationAndCombinationMapper physicalExaminationAndCombinationMapper;
    //用户和体检项的关系Mapper
    @Autowired
    private PhysicalExaminationAndCheckMapper physicalExaminationAndCheckMapper;


    /**
     * 根据身份证号查询用户信息表
     * @param personIdCard
     * @return
     */
    @Override
    @Transactional
    public PersonInfo getPersonInfoByNameAndCard(String personIdCard){
        PersonInfo personInfo=null;
        try {
            personInfo=personInfoMapper.findPersonInfoPersonIdCard(personIdCard);
            return personInfo;
        } catch (Exception e) {
            logger.error("根据身份证号查询用户表："+e.getMessage());
            e.printStackTrace();
            return null;
        }
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
    @Override
    @Transactional
    public int UserReservation(PersonInfo personInfo,Date Yudate) {
        int result = 0;
        try {
            PersonInfo per=personInfoMapper.findPersonInfoPersonIdCard(personInfo.getPersonIdCard());
            if (per==null) {
                result = personInfoMapper.addPersonInfo(personInfo);
            }else {
                personInfo.setPersonId(per.getPersonId());
                result = personInfoMapper.updPersonInfo(personInfo);
            }
            if (result < 0) {
                //添加(修改)用户信息失败
                logger.error("添加用户信息失败");
                return -2;
            }
            PhysicalExamination physicalExamination = new PhysicalExamination();
            //把日期转换为字符串yyy-MM-dd
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String YudateS = simpleDateFormat.format(Yudate);
            //根据预约日期查询预约日期的最后一位编号
            String physicalExaminationId = physicalExaminationMapper.getPhysicalExaminationOrderByMedicalTime(YudateS);
            //编号
            String phyNo = physicalExaminationId.substring(8,physicalExaminationId.length());
            //日期
            String phyDate = physicalExaminationId.substring(0,8);
            //生成新的编号
            Integer no = Integer.parseInt(phyNo)+1;
            phyNo = no.toString().length()==1?"0"+no+"":no + "";
            phyNo = phyDate + phyNo;
            physicalExamination.setPhysicalExaminationId(phyNo);
            physicalExamination.setPersonId(personInfo.getPersonId());
            physicalExamination.setMedicalTime(Yudate);
            //给这个人员生成体检编号
            result = physicalExaminationMapper.addPhysicalExaminationInfo(physicalExamination);
            if (result < 0) {
                //添加用户预约编号失败
                logger.error("添加用户预约编号失败");
                return -3;
            }
        } catch (Exception e) {
            logger.error("预约失败，"+e.getMessage());
            e.printStackTrace();
            return -1;
        }
        return result;
    }

    /**
     * 获取所有的检查项
     * @return
     */
    @Override
    @Transactional
    public List<Check> getAllCheck(){
        try {
            return checkMapper.getAllCheckList();
        } catch (Exception e) {
            logger.error("获取所有体检项："+e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取所有组合项
     * @return
     */
    @Override
    @Transactional
    public List<Combination> getAllCombination(){
        try {
            return combinationMapper.getAllCombinationList();
        } catch (Exception e) {
            logger.error("获取所有组合项："+e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取所有套餐
     * @return
     */
    @Override
    @Transactional
    public List<Package> getPackages() {
        List<Package> packageList = new ArrayList<Package>();
        Package packageCombination=null;
        Package packageCheck=null;
        Package pack = null;
        try {
            List<Integer> packageIdList = packageIdList = packageMapper.getAllPackageId();
            for (Integer packageId:packageIdList) {
                packageCombination =  packageMapper.getPackageCombination(packageId);
                packageCheck =  packageMapper.getPackageCheck(packageId);
                if(packageCombination!=null){
                    if(packageCheck!=null)
                        packageCombination.setPackageCheckList(packageCheck.getPackageCheckList());
                    packageList.add(packageCombination);
                }else {
                    pack= packageMapper.selPackageById(packageId);
                    if(packageCheck!=null)
                        pack.setPackageCheckList(packageCheck.getPackageCheckList());
                    packageList.add(pack);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return packageList;
    }

    /**
     * 新增用户选择的套餐、组合项、体检项
     * @param physicalExaminationId 体检编号
     * @param packId 所选的套餐项
     * @param comId 所选的组合项
     * @param checkId 所选的体检项
     * @return null
     */
    @Override
    @Transactional(rollbackFor=Exception.class)
    public Integer addPersonCheck(String physicalExaminationId,Integer[] packId,Integer[] comId,Integer[] checkId){
        try {
            physicalExaminationAndPackageMapper.addBatchPhyAndPackage(physicalExaminationId,packId);
            physicalExaminationAndCombinationMapper.addBatchPhysicalExaminationAndCombination(physicalExaminationId,comId);
            physicalExaminationAndCheckMapper.addBatchPhysicalExaminationAndCheck(physicalExaminationId,checkId);
        } catch (Exception e) {
            logger.error("用户选择套餐、组合项、体检项："+e.getMessage());
            e.printStackTrace();
            return null;
        }
        return 1;
    }
}