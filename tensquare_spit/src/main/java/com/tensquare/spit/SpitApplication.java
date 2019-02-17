package com.tensquare.spit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

import util.IdWorker;
import util.MyPageQuery;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)  //链接MongoDB 必须排除DataSourceAutoConfiguration.class
public class SpitApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpitApplication.class,args);
    }

    @Bean
    public IdWorker idWorker(){
        return new IdWorker(1,1);
    }

    @Bean
    public MyPageQuery myPageQuery(){
        return new MyPageQuery();
    }


}
