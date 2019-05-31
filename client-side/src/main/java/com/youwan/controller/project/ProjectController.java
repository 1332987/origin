package com.youwan.controller.project;

import com.youwan.common.dao.project.EnterpriseDao;
import com.youwan.common.entity.project.Enterprise;
import com.youwan.common.entity.project.ProjectManage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/project")
@Slf4j
public class ProjectController {

    @Autowired
    public EnterpriseDao enterpriseDao;

    @RequestMapping("/info")
    public String info(Model model) {
        Enterprise enterprise = enterpriseDao.getOne(1L);
        log.info("0.0" + enterprise.toString());
        log.info("lailelaodi");
        model.addAttribute("enterprise", enterprise);
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

    @RequestMapping("/updateFrom")
    public String updateFrom(Model model, ProjectManage projectManage) {
        log.info("updateFrom");
        log.info("0.0" + projectManage.toString());


        Enterprise enterprise = enterpriseDao.getOne(1L);
        log.info("0.0" + enterprise.toString());
        log.info("lailelaodi");
        model.addAttribute("enterprise", enterprise);
        return "project/info";
    }

}
