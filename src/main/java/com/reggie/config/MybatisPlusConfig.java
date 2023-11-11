package com.reggie.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//这个注解标记这个类为配置类，告诉Spring这是一个配置类
@Configuration

//配置分页的插件
public class MybatisPlusConfig {

    //用Bean注解标记下面的方法，告诉Spring这个方法将返回一个对象，该对象应被注册为一个Spring Bean
    //Bean是用于创建，配置，管理对象的一种方式
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        //创建一个拦截器对象
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();

        //向对象mybatisPlusInterceptor里，添加一个内部拦截器，用于分页
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());

        return mybatisPlusInterceptor;
    }
}
