package com.tensquare.qa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import util.IdWorker;
import util.JwtUtil;
import util.MyPageQuery;

@EnableCaching
@EnableEurekaClient //开启Eureka 客户端
@EnableFeignClients     //采用Feign的方式去发现服务
@EnableDiscoveryClient //发现服务
 @SpringBootApplication
public class QaApplication {
    public static void main(String[] args) {
        SpringApplication.run(QaApplication.class,args);
    }

    @Bean
    public IdWorker idWorker(){
        return new IdWorker(1,1);
    }
    
    @Bean
    public MyPageQuery myPageQuery(){
        return new MyPageQuery();
    }

    @Bean
    public JwtUtil jwtUtil(){
        return new JwtUtil();
    }
}
