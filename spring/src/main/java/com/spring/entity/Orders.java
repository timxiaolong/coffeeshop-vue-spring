package com.spring.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
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
 * @since 2023-12-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Orders对象", description="")
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "下单时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime ordertime;

    @ApiModelProperty(value = "下单用户名称")
    private String orderusername;

    @ApiModelProperty(value = "下单用户id")
    private Integer orderuserid;

    @ApiModelProperty(value = "下单商品")
    private String orderitemname;

    @ApiModelProperty(value = "下单商品id")
    private Integer orderitemid;

    @ApiModelProperty(value = "数量")
    private Integer num;

    @ApiModelProperty(value = "预计出餐时间")
    private LocalDateTime pretime;

    @ApiModelProperty(value = "订单价格")
    private BigDecimal price;

    @ApiModelProperty(value = "订单状态")
    private Integer status;

    @ApiModelProperty(value = "评价状态")
    @TableField("hasComm")
    private String hascomm;

    @ApiModelProperty(value = "商品照片")
    private String picture;
}
