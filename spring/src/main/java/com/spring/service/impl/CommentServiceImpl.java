package com.spring.service.impl;

import com.spring.entity.Comment;
import com.spring.mapper.CommentMapper;
import com.spring.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wyj
 * @since 2023-12-12
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
