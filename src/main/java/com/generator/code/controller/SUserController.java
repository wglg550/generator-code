package com.generator.code.controller;

import com.generator.code.service.SUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import io.swagger.annotations.Api;

/**
* @Description:SUserController
* @Param:
* @return:
* @Author: wangliang
* @Date: 2020-01-20
*/
@Api(tags = "SUserController")
@RestController
@RequestMapping("/SUser")
public class SUserController {

    @Resource
    SUserService sUserService;
}