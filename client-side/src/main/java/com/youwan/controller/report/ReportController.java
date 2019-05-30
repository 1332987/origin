package com.youwan.controller.report;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/report")
@Slf4j
public class ReportController {

    @RequestMapping("/formss")
    public String formss(Model model) {
        log.info("formss");
        return "report/formss";
    }
}
