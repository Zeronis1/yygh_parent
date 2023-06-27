package com.atguigu.yygh.cmn;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Date 2023/6/27
 * @Author SLF
 * @Description:
 */
@MapperScan("com.atguigu.yygh.cmn.mapper")
@SpringBootApplication
@ComponentScan("com.atguigu.yygh")
public class ServiceCmnApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceCmnApplication.class,args);
    }
}
