package com.atguigu.yygh.cmn.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Date 2023/6/28
 * @Author SLF
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @ExcelProperty("编号")
    private Long id;

    @ExcelProperty("姓名")
    private String name;

    @ExcelProperty("年龄")
    private Integer age;
}
