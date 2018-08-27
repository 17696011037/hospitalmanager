package com.dyhc.hospitalmanager.controller;

import com.alibaba.fastjson.JSON;
import com.dyhc.hospitalmanager.pojo.CheckResult;
import com.dyhc.hospitalmanager.pojo.CommonResults;
import com.dyhc.hospitalmanager.pojo.MedicalEvents;
import com.dyhc.hospitalmanager.pojo.ProposedDescription;
import com.dyhc.hospitalmanager.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class SectionController {
    @Autowired
    private SectionService sectionService;

    /**
     * 根据体检编号查询人员
     *  根据体检编号查询人员在该科室下的体检项
     * @param peacId
     * @return
     */

    @RequestMapping("Person.html")
    @ResponseBody
    public String getPersonCheckBySectionId(@RequestParam("peacId") String peacId) {
        Map<String,Object> map = sectionService.getPersonCheckBySectionId(peacId, 1);
        return JSON.toJSONString(map);
    }
    @RequestMapping("getCommResultsByCheckId.html")
    @ResponseBody
    public String getCommResultsByCheckId(@RequestParam("checkId")Integer checkId){
        List<CommonResults> list=sectionService.getCommResultsByCheckId(checkId,1);
        return JSON.toJSONString(list);
    }

    @RequestMapping("getProposedByResultId.html")
    @ResponseBody
    public String getProposedByResultId(@RequestParam("resultId")Integer resultId){
        List<ProposedDescription> list=sectionService.getProposedByResultId(resultId,1);
        return JSON.toJSONString(list);
    }
    @RequestMapping("addResultAndMedicalEvent.html")
    @ResponseBody
    public String addResultAndMedicalEvent(CheckResult checkResult, MedicalEvents medicalEvents){
        Integer add=sectionService.addCheckResultAndMedicalEvent(checkResult,medicalEvents,1);
        String json="";
        if(add>0){
            json="ok" ;
        }else{
            json="no";
        }
        return JSON.toJSONString(json);
    }




    }
