package com.reggie.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

//套餐菜品关系
@Data
public class SetmealDish implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long setmealId;//'套餐id ',
    private Long dishId;//'菜品id',
    private String name;//'菜品名称 （冗余字段）',
    private BigDecimal price;//'菜品原价（冗余字段）',
    private Integer copies;//'份数',
    private Integer sort;//'排序',

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime create_time;//'创建时间',
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime update_time;//'更新时间',

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime create_user;//'创建人',
    @TableField(fill=FieldFill.INSERT_UPDATE)
    private LocalDateTime update_user;//'修改人',
    private Integer is_deleted;//'是否删除',

}
