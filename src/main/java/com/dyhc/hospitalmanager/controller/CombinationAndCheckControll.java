package com.dyhc.hospitalmanager.controller;

import com.dyhc.hospitalmanager.service.CombinationAndCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CombinationAndCheckControll{

    @Autowired
    private CombinationAndCheckService combinationAndCheckService;
}
