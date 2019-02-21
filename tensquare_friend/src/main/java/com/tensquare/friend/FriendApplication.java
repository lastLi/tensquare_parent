package com.tensquare.friend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import util.JwtUtil;

@EnableEurekaClient //开启Eureka 客户端
@EnableFeignClients     //采用Feign的方式去发现服务,调用别的模块
@EnableDiscoveryClient //发现服务,调用别的模块
@SpringBootApplication
public class FriendApplication {

    public static void main(String[] args) {
        SpringApplication.run(FriendApplication.class,args);
    }


    @Bean
    public JwtUtil jwtUtil(){
        return new JwtUtil();
    }
}
