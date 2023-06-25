package com.atguigu.yygh.common.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Date 2023/6/25
 * @Author SLF
 * @Description:
 */
@Configuration
public class HospPlusConfig {

    /**
     * mybatis-plus的分页拦截器
     * 注入limit到sql语句中
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
