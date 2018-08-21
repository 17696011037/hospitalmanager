package com.dyhc.hospitalmanager.service.impl;

import com.dyhc.hospitalmanager.dao.PhysicalExaminationMapper;
import com.dyhc.hospitalmanager.pojo.PhysicalExamination;
import com.dyhc.hospitalmanager.service.PhysicalExaminationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class PhysicalExaminationServiceImpl implements PhysicalExaminationService {

    @Autowired
    private PhysicalExaminationMapper physicalExaminationMapper;

    /**
     * 添加体检信息
     * @param physicalExamination
     * @return
     * @throws Exception
     */
    @Override
    public int addPhysicalExaminationInfo(PhysicalExamination physicalExamination) throws Exception {
        return 0;
    }

    /**
     * 根据当前日期查询体检表倒叙第一条的体检编号
     * @return
     * @throws Exception
     */
    @Override
    public String getPhysicalExaminationOrderByMedicalTime() throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        String now = df.format(new Date());
        return physicalExaminationMapper.getPhysicalExaminationOrderByMedicalTime(now);
    }
}