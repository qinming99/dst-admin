package com.tugos.dst.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author qinming
 * @date 2020-05-17
 * <p>饥荒管理平台</p>
 */
@SpringBootApplication
@EnableScheduling
@Slf4j
public class AdminApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("dst-admin-饥荒管理平台-启动成功(Successfully started)......");
    }
}
