package io.redick.cloud.code.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import io.redick.cloud.code.GeneratorConfig;
import io.redick.cloud.code.utils.XmlParserUtil;
import org.dom4j.DocumentException;

import java.io.*;
import java.net.URL;
import java.util.Collections;
import java.util.List;

/**
 * @author Redick01
 */
public class MyBatisCodeGenerator {

    public static void main(String[] args) throws IOException {
        URL url = ClassLoader.getSystemClassLoader().getResource("generator.xml");
        if (url == null) {
            throw new IllegalStateException("generator.xml not found!");
        }
        String path = url.getPath();
        try (FileReader reader = new FileReader(new File(path))) {
            StringBuilder stringBuilder = new StringBuilder();
            char[] buffer = new char[10];
            int size;
            while ((size = reader.read(buffer)) != -1) {
                stringBuilder.append(buffer, 0, size);
            }
            GeneratorConfig config = new GeneratorConfig();
            XmlParserUtil.parse(config, stringBuilder.toString());
            generator(config);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }


    public static void generator(GeneratorConfig config) {
        //创建一个代码生成器
        FastAutoGenerator.create(config.getUrl(), config.getUsername(), config.getPassword())
                //全局配置(GlobalConfig)
                .globalConfig(builder -> {
                    builder.author(config.getAuthor()) // 设置作者，可以写自己名字
                            //.enableSwagger() // 开启 swagger 模式，这个是接口文档生成器，如果开启的话，就还需要导入swagger依赖
                            .dateType(DateType.TIME_PACK) //时间策略
                            .commentDate("yyyy-MM-dd") //注释日期
                            .outputDir(config.getOutputDir()); // 指定输出目录，一般指定到java目录
                })
                //包配置(PackageConfig)
                .packageConfig(builder -> {
                    builder.parent(config.getParentPackage()) // 设置父包名
                            .moduleName("") // 设置父包模块名，这里一般不设置
                            .pathInfo(Collections.singletonMap(OutputFile.xml, config.getMapperPath())); // 设置mapperXml生成路径，这里是Mapper配置文件的路径，建议使用绝对路径
                })
                //策略配置(StrategyConfig)
                .strategyConfig(builder -> {
                    List<String> tables = config.getTables();
                    // 设置需要生成的表名
                    tables.forEach(builder::addInclude);
                    builder.addTablePrefix(config.getTablePrefix());
                    builder.serviceBuilder()
                            .formatServiceFileName(config.getService()) //设置service的命名策略,没有这个配置的话，生成的service和serviceImpl类前面会有一个I，比如IUserService和IUserServiceImpl
                            .formatServiceImplFileName(config.getServiceImpl()); //设置serviceImpl的命名策略
                    builder.controllerBuilder()
                            .enableRestStyle(); // 开启生成@RestController 控制器，不配置这个默认是Controller注解，RestController是返回Json字符串的，多用于前后端分离项目。
                    builder.mapperBuilder()
                            .enableMapperAnnotation() ;//开启 @Mapper 注解，也就是在dao接口上添加一个@Mapper注解，这个注解的作用是开启注解模式，就可以在接口的抽象方法上面直接使用@Select和@Insert和@Update和@Delete注解。
                })
                .templateEngine(new VelocityTemplateEngine())
                .execute(); //执行以上配置
    }
}
