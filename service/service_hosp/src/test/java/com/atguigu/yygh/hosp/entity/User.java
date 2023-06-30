package com.atguigu.yygh.hosp.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Date 2023/6/28
 * @Author SLF
 * @Description:
 */
@Data
@Document("User")
public class User {

    @Id
    private String id;

    private String name;

    private Integer age;

    private String email;

    private String createDate;
}
