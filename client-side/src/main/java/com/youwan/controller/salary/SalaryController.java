package com.youwan.controller.salary;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/salary")
@Slf4j
public class SalaryController {

    @RequestMapping("/scheduling")
    public String scheduling(Model model) {
        log.info("scheduling");
        return "salary/scheduling";
    }

    @RequestMapping("/persons")
    public String persons(Model model) {
        log.info("persons");
        return "salary/persons";
    }

    @RequestMapping("/classess")
    public String classess(Model model) {
        log.info("classess");
        return "salary/classess";
    }
}
