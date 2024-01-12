package com.spring.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
@ApiModel(value="Comment对象", description="")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "评价id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户id")
    private Integer userid;

    @ApiModelProperty(value = "用户名称")
    private String username;

    @ApiModelProperty(value = "商品id")
    private Integer itemid;

    @ApiModelProperty(value = "商品名称")
    private String itemname;

    @ApiModelProperty(value = "评分")
    private String rating;

    @ApiModelProperty(value = "评论内容")
    private String commentcontent;

    @ApiModelProperty(value = "评论时间")
    private LocalDateTime commenttime;


}
