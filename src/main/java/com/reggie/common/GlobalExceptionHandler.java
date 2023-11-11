package com.reggie.common;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

//声明这是一个全局异常处理类，并指定它应用于所有使用@RestController和@Controller注解的类
@ControllerAdvice(annotations = {RestController.class, Controller.class})

//该类的方法返回值，应作为HTTP响应体
@ResponseBody

//Lombok提供的注解，用于在这个类中自动创建Slf4j(simple logging facade for Java）日志对象
@Slf4j

public class GlobalExceptionHandler {

    //异常处理方法
    //声明下面的方法是一个异常处理方法，用于处理SQLIntegrityConstrainViolationException类型的异常
    @ExceptionHandler(SQLIntegrityConstraintViolationException .class)

    //定义exceptionHandler方法，接收一个SQLIntegrityConstraintViolationException类型的参数
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException ex) {

        //使用Slf4j记录异常信息
        log.error(ex.getMessage());

        //检查异常信息，是否包含Duplicate entry
        //这里getMessage()会获取到，系统抛出来的异常信息：Duplicate entry "用户名" 。。。
        if (ex.getMessage().contains("Duplicate entry")) {
            //将异常信息，按空格分割
            String[] split = ex.getMessage().split(" ");
            //获取分割后的第三个元素，并拼接"已存在"
            String msg = split[2] + "已存在";
            //返回一个错误响应，其中包含自定义的错误信息
            return R.error(msg);
        }

        //如果异常消息不包含"Duplicate entry"，则返回一个包含"未知错误"的错误响应信息
        return R.error("未知错误");
    }
}
