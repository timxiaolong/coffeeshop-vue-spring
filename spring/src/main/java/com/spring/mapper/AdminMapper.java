package com.spring.mapper;

import com.spring.entity.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wyj
 * @since 2023-12-11
 */
@Mapper
public interface AdminMapper extends BaseMapper<Admin> {

}
