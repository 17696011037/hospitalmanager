package com.dyhc.hospitalmanager.controller;

import com.dyhc.hospitalmanager.service.CommonResultsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommonResultsController{

    @Autowired
    private CommonResultsService commonResultsService;
}