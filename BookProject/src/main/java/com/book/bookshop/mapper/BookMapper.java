package com.book.bookshop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.book.bookshop.entity.Book;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author:Yuzhiqiang
 * @Description:
 * @Date: Create in 22:40 2021/10/10
 * @Modified By:
 */
@Mapper
public interface BookMapper extends BaseMapper<Book> {
}
