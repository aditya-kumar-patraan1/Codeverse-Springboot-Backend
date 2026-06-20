package com.adityavikas.codeverse.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contestProblem")
@Tag(name="Contest problem API Controller (accessed right to admin/editor only)",description = "This contains all the endpoints to handle add,delete,edit options of contest's problem")
public class ContestProblemController {



}
