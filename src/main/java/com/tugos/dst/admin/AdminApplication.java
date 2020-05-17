package com.tugos.dst.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 注意事项：shell脚本第一份必须是：#!/bin/bash
 * 脚本名称用全小写字母
 */
@SpringBootApplication
@EnableScheduling
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }

}
