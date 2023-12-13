package com.spring.service.impl;

import com.spring.entity.Admin;
import com.spring.mapper.AdminMapper;
import com.spring.service.AdminService;
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
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

}
