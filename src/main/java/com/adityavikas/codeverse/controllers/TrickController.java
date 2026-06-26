package com.adityavikas.codeverse.controllers;

import com.adityavikas.codeverse.services.TrickService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trick")
public class TrickController {

    @Autowired
    private TrickService trickService;

}
