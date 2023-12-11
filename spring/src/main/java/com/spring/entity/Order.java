package com.spring.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author wyj
 * @since 2023-12-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Order对象", description="")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单id")
    private Integer id;

    @ApiModelProperty(value = "下单时间")
    private LocalDateTime ordertime;

    @ApiModelProperty(value = "下单用户名称")
    private String orderusername;

    @ApiModelProperty(value = "下单用户id")
    private Integer orderuserid;

    @ApiModelProperty(value = "预计出餐时间")
    private LocalDateTime pretime;

    @ApiModelProperty(value = "订单价格")
    private BigDecimal price;

    @ApiModelProperty(value = "订单状态")
    private Integer status;


}
