package com.generator.code.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @Description: jdbc配置文件
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/1/15
 */
@Configuration
@ConfigurationProperties(prefix = "mysql.jdbc", ignoreUnknownFields = false)
@Getter
@Setter
public class JdbcConfig {
    private String driveClass;
    private String database;
    private String ip;
    private String port;
    private String url;
    private String username;
    private String password;
    private String query;
    private List tables;
}
