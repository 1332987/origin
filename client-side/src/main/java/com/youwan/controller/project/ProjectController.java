package com.youwan.controller.project;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/project")
@Slf4j
public class ProjectController {

    @RequestMapping("/info")
    public String info(Model model) {
        log.info("lailelaodi");
        return "project/info";
    }

    @RequestMapping("/subpackage")
    public String subpackage(Model model) {
        log.info("subpackage");
        return "project/subpackage";
    }

    @RequestMapping("/addSubpackage")
    public String addSubpackage(Model model) {
        log.info("subpackage");
        return "project/addSubpackage";
    }

}
