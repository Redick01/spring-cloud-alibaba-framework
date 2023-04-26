package io.redick.cloud.datasource.configuration;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import io.redick.cloud.datasource.datasource.RoutingDataSource;
import io.redick.cloud.datasource.enums.DBTypeEnum;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Redick01
 */
@Configuration
public class DataSourceConfiguration {

    /**
     * 配置主数据源
     *
     * @return 数据源
     */
    @Bean(name = "master")
    @ConfigurationProperties(prefix = "spring.datasource.dynamic.datasource.master")
    public DataSource masterDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 配置从数据源
     *
     * @return 数据源
     */
    @Bean(name = "slave1")
    @ConfigurationProperties(prefix = "spring.datasource.dynamic.datasource.slave1")
    public DataSource slave1DataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 配置从数据源
     *
     * @return 数据源
     */
    @Bean(name = "slave2")
    @ConfigurationProperties(prefix = "spring.datasource.dynamic.datasource.slave2")
    public DataSource slave2DataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 配置路由数据源
     *
     * @param masterDataSource 主节点
     * @param slave1DataSource 从节点
     * @param slave2DataSource 从节点
     * @return 数据源
     */
    @Bean
    public DataSource routingDataSource(@Qualifier("master") DataSource masterDataSource,
                                          @Qualifier("slave1") DataSource slave1DataSource,
                                          @Qualifier("slave2") DataSource slave2DataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>(3);
        targetDataSources.put(DBTypeEnum.MASTER, masterDataSource);
        targetDataSources.put(DBTypeEnum.SLAVE1, slave1DataSource);
        targetDataSources.put(DBTypeEnum.SLAVE2, slave2DataSource);
        RoutingDataSource routingDataSource = new RoutingDataSource();
        //设置默认数据源
        routingDataSource.setDefaultTargetDataSource(masterDataSource);
        routingDataSource.setTargetDataSources(targetDataSources);
        return routingDataSource;
    }
}
