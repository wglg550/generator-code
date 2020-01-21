package com.generator.code.controller;

import com.generator.code.freemarker.FreemarkerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.support.MetaDataAccessException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    FreemarkerUtil freemarkerUtil;

    @RequestMapping(value = "/conn", method = RequestMethod.GET)
//    @GetMapping("/conn")
    public void conn() throws IOException, MetaDataAccessException {
        log.info("welcome conn");
        freemarkerUtil.createCode();
//        return null;
    }
}
