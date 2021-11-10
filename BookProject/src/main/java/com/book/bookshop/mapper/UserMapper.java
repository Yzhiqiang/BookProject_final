package com.book.bookshop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.book.bookshop.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author:Yuzhiqiang
 * @Description:
 * @Date: Create in 13:48 2021/10/11
 * @Modified By:
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
