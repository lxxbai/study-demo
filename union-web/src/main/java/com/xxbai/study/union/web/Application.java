package com.xxbai.study.union.web;

import cn.hutool.extra.spring.EnableSpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author xxbai
 */
@Slf4j
@SpringBootApplication(scanBasePackages = {"com.xxbai.study.union.web"})
@EnableSpringUtil
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        log.info("union-web Started ...");
    }
}
