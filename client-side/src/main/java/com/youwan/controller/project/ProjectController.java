package com.youwan.controller.project;

import com.alibaba.fastjson.JSON;
import com.youwan.common.dao.project.EnterpriseDao;
import com.youwan.common.dao.project.ProjectManageDao;
import com.youwan.common.dao.project.ProjectUnitDao;
import com.youwan.common.entity.project.Enterprise;
import com.youwan.common.entity.project.ProjectManage;
import com.youwan.common.entity.project.ProjectUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/project")
@Slf4j
public class ProjectController {

    @Autowired
    public EnterpriseDao enterpriseDao;
    @Autowired
    public ProjectManageDao projectManageDao;
    @Autowired
    public ProjectUnitDao projectUnitDao;

    @RequestMapping("/info")
    public String info(Model model) {
        Enterprise enterprise = enterpriseDao.getOne(1L);
        log.info("0.0" + enterprise.toString());
        log.info("lailelaodi-1");
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

    @RequestMapping("/addSubpackageP")
    public String addSubpackageP(Model model, ProjectUnit projectUnit) {
        log.info("addSubpackageP");
        projectUnitDao.save(projectUnit);
        return "project/addSubpackage";
    }

    @RequestMapping("/updateFrom")
    public String updateFrom(Model model, ProjectManage projectManage) {
        log.info("updateFrom");
        log.info("0.0" + projectManage.toString());
        projectManageDao.save(projectManage);

//        Enterprise enterprise = enterpriseDao.getOne(1L);
//        log.info("0.0" + enterprise.toString());
        log.info("lailelaodi-2");
//        model.addAttribute("enterprise", enterprise);
        return "project/info";
    }

    @ResponseBody
    @RequestMapping("tableT")
    public String tableT() {
        List<ProjectUnit> projectList = projectUnitDao.findAll();
        for (ProjectUnit projectUnit : projectList) {
            log.info(projectUnit.toString());
        }
        return JSON.toJSONString(projectList);
    }
}
