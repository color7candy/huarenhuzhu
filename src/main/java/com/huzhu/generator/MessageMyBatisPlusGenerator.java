package com.huzhu.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

public class MessageMyBatisPlusGenerator {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://127.0.0.1:3306/huzhu", "root","efkllx12")
                .globalConfig(builder -> {
                    builder.author("user")
                            .fileOverride()
                            .outputDir(System.getProperty("user.dir")+"/src/main/java");
                })
                .packageConfig(builder -> {
                    builder.parent("com.huzhu")
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml,System.getProperty("user.dir")+"/src/main/resources/mapper/"));
                })
                .strategyConfig(builder -> {
                    builder.addInclude("message");
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}
