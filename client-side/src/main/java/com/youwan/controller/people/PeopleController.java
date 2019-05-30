package com.youwan.controller.people;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/people")
@Slf4j
public class PeopleController {

    @RequestMapping("/dictionaries")
    public String dictionaries(Model model) {
        log.info("lailelaodi");
        return "people/dictionaries";
    }

    @RequestMapping("/management")
    public String management(Model model) {
        log.info("lailelaodi");
        return "people/management";
    }
}
