package com.dyhc.hospitalmanager.service.impl;

import com.dyhc.hospitalmanager.dao.RoleInfoMapper;
import com.dyhc.hospitalmanager.service.RoleInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleInfoServiceImpl implements RoleInfoService {

    @Autowired
    private RoleInfoMapper roleInfoMapper;
}