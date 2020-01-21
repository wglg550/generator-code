package com.generator.code.dao;

import com.generator.code.entity.SUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
* @Description:SUserRepo dao
* @Param:
* @return:
* @Author: wangliang
* @Date: 2020-01-20
*/
@Repository
public interface SUserRepo extends JpaRepository<SUserEntity, Long>, JpaSpecificationExecutor<SUserEntity> {

}