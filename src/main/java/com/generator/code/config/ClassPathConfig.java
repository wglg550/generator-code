package com.generator.code.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * @Description: 路径配置文件
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/1/15
 */
@Configuration
@ConfigurationProperties(prefix = "class.path", ignoreUnknownFields = false)
@Getter
@Setter
public class ClassPathConfig {
    private String system;
    private String project;
    private String service;
    private String serviceImpl;
    private String dao;
    private String entity;
    private String controller;
    private String template;
    private String author;
    private String outsidePath;
    private String outsideProject;

    public void setService(String service) {
        if (Objects.nonNull(getOutsideProject()) && !Objects.equals(getOutsideProject(), "")) {
            this.service = new StringJoiner("").add(getOutsideProject()).add("/service").toString();
        } else {
            this.service = service;
        }
    }

    public void setServiceImpl(String serviceImpl) {
        if (Objects.nonNull(getOutsideProject()) && !Objects.equals(getOutsideProject(), "")) {
            this.serviceImpl = new StringJoiner("").add(getOutsideProject()).add("/service/impl").toString();
        } else {
            this.serviceImpl = serviceImpl;
        }
    }

    public void setDao(String dao) {
        if (Objects.nonNull(getOutsideProject()) && !Objects.equals(getOutsideProject(), "")) {
            this.dao = new StringJoiner("").add(getOutsideProject()).add("/dao").toString();
        } else {
            this.dao = dao;
        }
    }

    public void setEntity(String entity) {
        if (Objects.nonNull(getOutsideProject()) && !Objects.equals(getOutsideProject(), "")) {
            this.entity = new StringJoiner("").add(getOutsideProject()).add("/entity").toString();
        } else {
            this.entity = entity;
        }
    }

    public void setController(String controller) {
        if (Objects.nonNull(getOutsideProject()) && !Objects.equals(getOutsideProject(), "")) {
            this.controller = new StringJoiner("").add(getOutsideProject()).add("/controller").toString();
        } else {
            this.controller = controller;
        }
    }
}
