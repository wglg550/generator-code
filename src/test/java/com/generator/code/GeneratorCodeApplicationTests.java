package com.generator.code;

import com.generator.code.freemarker.FreemarkerUtil;
import com.generator.code.jdbc.JdbcConnect;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.support.MetaDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class GeneratorCodeApplicationTests {

    @Resource
    JdbcConnect jdbcConnect;

    @Resource
    FreemarkerUtil freemarkerUtil;

//    @Test
//    public void springJdbcConn() throws SQLException, MetaDataAccessException {
//        Multimap<String, Map<String, String>> map = jdbcConnect.springJdbcConn();
//        log.info("map.size:{}", map.size());
////        jdbcConnect.jdbcConn();
//    }

    @Test
    public void freemarkerUtil() throws IOException, MetaDataAccessException {
        freemarkerUtil.createCode();
    }
}
