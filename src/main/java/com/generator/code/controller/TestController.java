package com.generator.code.controller;

import com.alibaba.fastjson.JSONObject;
import com.generator.code.dao.SUserRepo;
import com.generator.code.entity.SUserEntity;
import com.generator.code.freemarker.FreemarkerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.support.MetaDataAccessException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    FreemarkerUtil freemarkerUtil;

    @Resource
    SUserRepo sUserRepo;

    @RequestMapping(value = "/conn", method = RequestMethod.GET)
//    @GetMapping("/conn")
    public void conn() throws IOException, MetaDataAccessException {
        log.info("welcome conn");
//        freemarkerUtil.createCode();
        List<SUserEntity> list = sUserRepo.findAll();
        log.info("list:{}", JSONObject.toJSONString(list));
//        return null;
    }
}
