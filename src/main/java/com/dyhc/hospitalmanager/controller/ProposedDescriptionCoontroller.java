package com.dyhc.hospitalmanager.controller;

import com.dyhc.hospitalmanager.service.ProposedDescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProposedDescriptionCoontroller {

    @Autowired
    private ProposedDescriptionService proposedDescriptionService;
}