package com.youwan.controller.project;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller()
@Slf4j
public class Project {

    @RequestMapping("/project/info")
    public String info(Model model){
        log.info("lailelaodi");
        return "project/info";
    }

}
