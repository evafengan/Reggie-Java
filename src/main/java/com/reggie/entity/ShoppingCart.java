package com.reggie.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ShoppingCart implements Serializable {
    private static final long serialVersionUID=1L;
    private Long id;
            private String name          ;//'名称',
            private String image         ;//'图片',
            private Long userId       ;//'主键',
            private Long dishId       ;//'菜品id',
            private Long setmealId    ;//'套餐id',
            private String dishFlavor   ;//'口味',
            private Integer number        ;//'数量',
            private BigDecimal amount        ;//'金额',
            private LocalDateTime createTime   ;//'创建时间',
}