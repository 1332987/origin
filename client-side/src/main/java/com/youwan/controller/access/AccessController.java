package com.youwan.controller.access;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/access")
@Slf4j
public class AccessController {

    @RequestMapping("/aisle")
    public String aisle(Model model) {
        log.info("lailelaodi");
        return "access/aisle";
    }

    @RequestMapping("/record")
    public String record(Model model) {
        log.info("lailelaodi");
        return "access/record";
    }
}
