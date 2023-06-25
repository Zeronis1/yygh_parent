package com.atguigu.yygh.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;

public class Generator {

    public static void main(String[] args) {
        //创建一个代码生成器
        FastAutoGenerator.create("jdbc:mysql://192.168.27.15:3306/yygh_hosp",
                "root", "root")
                //全局配置(GlobalConfig)
                .globalConfig(builder -> {
                    builder.author("atguigu") // 设置作者
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("D:\\IDEA\\workspace\\Advanced\\yygh_parent\\service\\service_hosp\\src\\main\\java") // 指定输出目录，一般指定到java目录
                            .disableOpenDir(); //禁止打开输出目录
                })
                //包配置(PackageConfig)
                .packageConfig(builder -> {
                    builder.parent("com.atguigu.yygh.hosp"); // 设置父包名
                })
                //策略配置(StrategyConfig)
                .strategyConfig(builder -> {
                    builder.addInclude("hospital_set"); // 设置表名,会根据该表生成代码
                    builder.entityBuilder()
                            .enableLombok() //开启 lombok 模型
                            .enableTableFieldAnnotation(); //生成字段注解
                    builder.serviceBuilder()
                            .formatServiceFileName("%sService") //设置service的命名策略
                            .formatServiceImplFileName("%sServiceImpl"); //设置serviceImpl的命名策略
                    builder.controllerBuilder()
                            .enableRestStyle(); //开启生成@RestController控制器，不配置这个默认是@Controller注解
                })
                .execute(); //执行以上配置
    }
}