package com.reggie.common;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

public class JacksonObjectMapper extends ObjectMapper {
    //常量字符串，用于默认的日期格式
    public static final String DEFAULT_DATE_FORMAT = "yyy-MM-dd";
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

    //定义JacksonObjectMapper类的构造函数
    public JacksonObjectMapper() {
        //调用父类ObjectMapper的构造函数
        super();

        //收到未知属性时不报异常
        this.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);

        //反序列化时，属性不存在的兼容处理。配置的反序列化行为，在遇到未知属性时不会失败
        this.getDeserializationConfig().withoutFeatures(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        //创建一个新的SimpleModule对象，用于添加自定义的序列化器和反序列化器
        SimpleModule simpleModule = new SimpleModule()

//        序列化: 将 Java 对象 { name: "John", age: 30 } 转换为 JSON 字符串 {"name":"John","age":30}
//        反序列化: 将 JSON 字符串 {"name":"John","age":30} 转换回 Java 对象 { name: "John", age: 30 }
                //添加自定义的反序列化器，用于处理LocalDateTime和LocalDate，LocalTime
                .addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)))
                .addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)))
                .addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)))

                //添加自定义的序列化器，用于处理BigInteger，Long，LocalDateTime，LocalDate,LocalTime
                .addSerializer(BigInteger.class,  ToStringSerializer.instance)
                //将long型数字，转为string。比如，Employee表里的id字段是long型，如果由前端js提交给后端的话，会丢失精度
                //所以，后端统一将其转为string，这样就不会丢失精度
                .addSerializer(Long.class, ToStringSerializer.instance)
                .addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)))
                .addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)))
                .addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)));

        //注册功能模块，例如，可以添加自定义序列化器和反序列化器
        //将自定义的SimpleModule注册到ObjectMappler
        this.registerModule(simpleModule);
    }

}
