package com.dyhc.hospitalmanager.service.impl;

import com.dyhc.hospitalmanager.dao.*;
import com.dyhc.hospitalmanager.pojo.*;
import com.dyhc.hospitalmanager.pojo.Package;
import com.dyhc.hospitalmanager.service.BasicFunctionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.ldap.PagedResultsControl;
import java.util.List;

@Service
public class BasicFunctionServiceImpl implements BasicFunctionService {

    private Logger logger= LoggerFactory.getLogger(BasicFunctionService.class);
    @Autowired
    private SectionMapper sectionMapper;
    @Autowired
    private CheckMapper checkMapper;
    @Autowired
    private CombinationMapper combinationMapper;
    @Autowired
    private PackageMapper packageMapper;
    @Autowired
    private CommonResultsMapper commonResultsMapper;
    @Autowired
    private ProposedDescriptionMapper proposedDescriptionMapper;
    @Autowired
    private CombinationAndCheckMapper combinationAndCheckMapper;
    @Autowired
    private PackageAndCheckMapper packageAndCheckMapper;
    @Autowired
    private PackageAndCombinationMapper packageAndCombinationMapper;


    //----------------------------------科室维护
    @Override
    public int addSectionInfo(Section section) {
        int result=0;
        try {
            result=sectionMapper.addSectionInfo(section);
        } catch (Exception e) {
            logger.error("科室添加错误!");
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int updSectionInfo(Section section) {
        int result=0;
        try {
            result=sectionMapper.updSectionInfo(section);
        } catch (Exception e) {
            logger.error("科室修改错误!");
            e.printStackTrace();
        }
        return result;
    }


    @Override
    public List<Section> getSectionInfoById(Integer sectionId) {
        List<Section>list=null;
        try {
            list=sectionMapper.getSectionInfoById(sectionId);
        } catch (Exception e) {
            logger.error("根据主键id查询科室信息失败");
            e.printStackTrace();
        }
        return list;
    }


    //----------------------------------体检项维护

    @Override
    public List<Check> getAllCheckList() {
        return null;
    }

    @Override
    public int addCheckInfo(Check check) {
        int result=0;
        try {
            result=checkMapper.addCheckInfo(check);
        } catch (Exception e) {
            logger.error("体检项添加错误");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据体检项id查询体检项信息
     * @param checkId
     * @return
     */
    @Override
    public List<Check> getAllCheckInfoById(Integer checkId) {
        List<Check>list=null;
        try {
            list=checkMapper.getAllCheckInfoById(checkId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int updCheckInfo(Check check) {
        int result=0;
        try {
            result=checkMapper.updCheckInfo(check);
        } catch (Exception e) {
            logger.error("体检项修改错误");
            e.printStackTrace();
        }
        return result;
    }




    //----------------------------------组合项项维护

    /**
     * 根据科室id查询体检信息
     * @param sectionId
     * @return
     */
    @Override
    public List<Check> getCheckListBySectionId(Integer sectionId) {
        List<Check>list=null;
        try {
            list=checkMapper.getCheckListBySectionId(sectionId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int addCombinationInfo(Combination combination,Integer[] checkList) {
        int result=0;
        try {
            result=combinationMapper.addCombinationInfo(combination);
            //添加组合与体检项的关系
            for(Integer checkId : checkList){
                CombinationAndCheck combinationAndCheck = new CombinationAndCheck();
                combinationAndCheck.setCombinationId(combination.getCombinationId());
                combinationAndCheck.setCheckId(checkId);
                combinationAndCheckMapper.addCombinationAndCheck(combinationAndCheck);
            }
        } catch (Exception e) {
            logger.error("组合项添加失败!");
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 修改组合项信息
     * @param combination
     * @param
     * @return
     */
    @Override
    public int updCombinationInfo(Combination combination) {
        int result=0;
        try {
            result=combinationMapper.updCombinationInfo(combination);
        } catch (Exception e) {
            logger.error("组合项修改失败!");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据组合项id查询组合项以及下的体检项信息
     * @param combinationId
     * @return
     */
    @Override
    public List<Combination> getCheckByCombinationId(Integer combinationId) {
        List<Combination> list=null;
        try {
            list=combinationMapper.getCheckByCombinationId(combinationId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Check> getSectionAndCheckAndCombinationInfo() {
        List<Check>list=null;
        try {
            list=sectionMapper.getSectionAndCheckAndCombinationInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Check> getSectionAndCheckAndCombinationInfoById(Integer sectionId) {

        List<Check>list=null;
        try {
            list=sectionMapper.getSectionAndCheckAndCombinationInfoById(sectionId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //----------------------------------套餐项维护

    /**
     * 获取所有套餐信息
     * @return
     */
    @Override
    public List<Package> showAllPackage() {
        List<Package>list=null;
        try {
            list=packageMapper.showAllPackage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int addPackageInfo(Package pack,Integer[] combinationList,Integer[] checkList) {
        int result=0;
        try {
            result=packageMapper.addPackageInfo(pack);
            if(combinationList.length==0){
                //添加套餐与体检项的关系
                for (Integer checkId  : checkList){
                    PackageAndCheck packageAndCheck = new PackageAndCheck();
                    packageAndCheck.setPackageId(pack.getPackageId());
                    packageAndCheck.setCheckId(checkId);
                    packageAndCheckMapper.addPackageAndCheckInfo(packageAndCheck);
                }
            }else if(checkList.length==0){
                //添加套餐与组合项的关系
                for (Integer comid : combinationList){
                    PackageAndCombination packageAndCombination = new PackageAndCombination();
                    packageAndCombination.setPackageId(pack.getPackageId());
                    packageAndCombination.setCombinationId(comid);
                    packageAndCombinationMapper.addPackageAndCombination(packageAndCombination);
                }
            }else{
                //添加套餐与体检项的关系
                for (Integer checkId  : checkList){
                    PackageAndCheck packageAndCheck = new PackageAndCheck();
                    packageAndCheck.setPackageId(pack.getPackageId());
                    packageAndCheck.setCheckId(checkId);
                    packageAndCheckMapper.addPackageAndCheckInfo(packageAndCheck);
                }
                //添加套餐与组合项的关系
                for (Integer comid : combinationList){
                    PackageAndCombination packageAndCombination = new PackageAndCombination();
                    packageAndCombination.setPackageId(pack.getPackageId());
                    packageAndCombination.setCombinationId(comid);
                    packageAndCombinationMapper.addPackageAndCombination(packageAndCombination);
                }
            }


        } catch (Exception e) {
            logger.error("添加套餐失败!");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据套餐id查询套餐信息以及下的组合项信息
     * @param packageId
     * @return
     */
    @Override
    public List<Package> getPackageAndCombinationInfoById(Integer packageId) {
        List<Package>list=null;
        try {
            list=packageMapper.getPackageAndCombinationInfoById(packageId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 根据套餐id查询套餐信息以及下的体检项下的信息
     * @param packageId
     * @return
     */
    @Override
    public List<Package> getPackageAndCheckInfoById(Integer packageId) {
        List<Package>list=null;
        try {
            list=packageMapper.getPackageAndCheckInfoById(packageId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 根据科室id查询组合项信息以及下的体检项信息
     * @param sectionId
     * @return
     * @throws Exception
     */
    @Override
    public List<Combination> getCombinationAndCheckInfoBySectionId(Integer sectionId)  {
        List<Combination>list=null;
        try {
            list=combinationMapper.getCombinationAndCheckInfoBySectionId(sectionId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    @Override
    public int updPackageInfo(Package pack) {
        int result=0;
        try {
            result=packageMapper.updPackageInfo(pack);
        } catch (Exception e) {
            logger.error("修改套餐失败!");
            e.printStackTrace();
        }
        return result;
    }


    //----------------------------------常见结果维护

    @Override
    public int addCommonResultsInfo(CommonResults commonResults) {
        int result=0;
        try {
            result=commonResultsMapper.addCommonResultsInfo(commonResults);
        } catch (Exception e) {
            logger.error("添加常见结果失败");
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int updCommonResultsInfo(CommonResults commonResults) {
        int result=0;
        try {
            result=commonResultsMapper.updCommonResultsInfo(commonResults);
        } catch (Exception e) {
            logger.error("修改常见结果失败");
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<CommonResults> getAllCommonResultsById(Integer commonResultsId) {
        List<CommonResults>list=null;
        try {
            list=commonResultsMapper.getAllCommonResultsById(commonResultsId);
        } catch (Exception e) {
            logger.error("根据主键id查询常见结果信息失败");
            e.printStackTrace();
        }
        return list;
    }


    //----------------------------------建议描述维护

    @Override
    public int addProposedDescriptionInfo(ProposedDescription proposedDescription) {
        int result=0;
        try {
            result=proposedDescriptionMapper.addProposedDescriptionInfo(proposedDescription);
        } catch (Exception e) {
            logger.error("添加建议描述失败!");
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int updProposedDescriptionInfo(ProposedDescription proposedDescription) {
        int result=0;
        try {
            result=proposedDescriptionMapper.updProposedDescriptionInfo(proposedDescription);
        } catch (Exception e) {
            logger.error("修改建议描述失败!");
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<ProposedDescription> getAllProposedDescriptionInfoById(Integer proposedDescriptionId) {
        List<ProposedDescription>list=null;
        try {
            list=proposedDescriptionMapper.getAllProposedDescriptionInfoById(proposedDescriptionId);
        } catch (Exception e) {
            logger.error("根据主键id查询建议描述信息失败");
            e.printStackTrace();
        }
        return list;
    }

    /**
     *查询科室信息以及其下的组合项和体检项信息
     * @return
     */
    @Override
    public List<Section> getSectionList() {
        try {
            List<Section> list = sectionMapper.getSectionList();
            for (Section section : list){
                section.setSectionandcheckList(checkMapper.getCheckListBySectionId(section.getSectionId()));
                section.setSectioandcombinationList(combinationMapper.getCombinationList(section.getSectionId()));
            }
            return list;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("查询科室信息以及其下的组合项和体检项信息失败");
            return null;
        }
    }

}
