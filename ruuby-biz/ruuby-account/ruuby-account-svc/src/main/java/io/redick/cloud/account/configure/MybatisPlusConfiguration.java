package io.redick.cloud.account.configure;

import io.redick.cloud.datasource.annotation.EnableMybatisPlugin;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Redick01
 */
@Configuration
@MapperScan("io.redick.cloud.account.mapper")
@EnableMybatisPlugin
public class MybatisPlusConfiguration {
}
