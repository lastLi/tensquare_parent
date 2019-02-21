package com.tensquare.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import util.IdWorker;
import util.MyPageQuery;

@EnableCaching
@EnableEurekaClient
@SpringBootApplication
public class BaseApplication {
    public static void main(String[] args) {
        SpringApplication.run(BaseApplication.class,args);
    }

    /**
     * 注册ID生成器
     * @return ID生成器
     */
    @Bean
    public IdWorker idWorker(){
        return new IdWorker(1,1);
    }


    @Bean
    public MyPageQuery myPageQuery(){
        return new MyPageQuery();
    }
}
