package com.generator.code.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Slf4j
@Controller
@RequestMapping("/html")
public class HtmlController {

    @RequestMapping(value = "/index", method = RequestMethod.GET)
//    @GetMapping("/index")
    public String index() {
        log.info("goIndexHtml");
        return "/html/index.html";
    }
}
