package com.generator.code.service.impl;

import com.generator.code.service.SUserService;
import org.springframework.stereotype.Service;
import com.generator.code.dao.SUserRepo;
import javax.annotation.Resource;

/**
* @Description:SUserServiceImpl接口
* @Param:
* @return:
* @Author: wangliang
* @Date: 2020-01-20
*/
@Service
public class SUserServiceImpl implements SUserService {

    @Resource
    SUserRepo sUserRepo;
}