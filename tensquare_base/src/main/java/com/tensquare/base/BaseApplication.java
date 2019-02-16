package com.tensquare.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import util.IdWorker;
import util.MyPageQuery;

@CrossOrigin //跨域请求
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
