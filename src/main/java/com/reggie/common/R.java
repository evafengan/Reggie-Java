package com.reggie.common;


import lombok.Data;

import java.util.HashMap;
import java.util.Map;

//通用返回结果，服务端响应的数据最终都会封装成此对象
//使用Lombok的data注解，自动生成getter,setter,equals,hashCode和toString方法
@Data

//定义一个泛型类R， T是数据类型
public class R<T> {

    private Integer code; //编码：1成功，0和其它数字为失败
    private String msg;  //错误信息
    private T data;  //数据
    private Map map=new HashMap();  //动态数据

    //<T>是一个占位符，代表一个未知类型。在实际调用这个方法时，由编译器去推断T是什么类型
    //R<T>是方法success的返回值类型
    public static <T> R<T> success(T object) {
        //创建对象时用R<T>，确保了r.data=object的类型一致，类型安全性
        R<T> r=new R<T>();
        r.data=object;
        r.code=1;
        return r;
    }

    public static <T> R<T> error(String msg) {
        //这里没有用到r.data，所以没有加<T>，其实在这个方法声明中，也不需要<T>
        R r=new R();
        r.msg=msg;
        r.code=0;
        return r;
    }

    public R<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }


}
