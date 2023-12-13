package com.spring.mapper;

import com.spring.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wyj
 * @since 2023-12-12
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

}
