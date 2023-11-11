package com.reggie.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

//订单明细
@Data
public class OrderDetail implements Serializable {

    public static final long serialVersionUID = 1L;

    private Long id;

    private String name;     // '名字',
    private Long orderId;     // '订单id',
    private Long dishId;     // '菜品id',
    private Long setmealId;     // '套餐id',
    private String dishFlavor;     //  '口味',
    private Integer number;     //    '数量',
    private BigDecimal amount;     //    '金额',
    private String image;
}
