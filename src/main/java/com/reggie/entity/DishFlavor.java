package com.reggie.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

//菜品口味
@Data
public class DishFlavor implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;

    //菜品id
//    Long 是一个包装类，它封装了基本数据类型 long 的对象。基本数据类型 long 是一个 64 位的整数数据类型，而 Long 提供了 long 的对象表示形式，以及一些操作 long 值的方法和属性。
//    使用 Long 而不是 long 的原因包括：
//    对象需求：在需要对象而不是基本类型时，例如在泛型集合中，如 ArrayList<Long>，因为泛型不支持基本数据类型。
//    空值：Long 可以为 null，而 long 不能。这可以用来表示值不存在或未初始化的状态。
//    类方法：Long 类提供了多种方法来处理数值，包括转换为其他类型，比较，解析字符串为 long，以及转换为二进制、八进制或十六进制字符串等。
    private Long dishId;
    //口味名称
    private String name;
    //口味数据list
    private String value;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

    //是否删除
    private Integer isDeleted;


}
