package com.jimu.conf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by bcl on 16/4/13.
 */
@Configuration
@MapperScan("com.jimu.dao")
@EnableTransactionManagement
//@ImportResource("classpath:spring-core.xml")
public class SpringConfiguration {
}
