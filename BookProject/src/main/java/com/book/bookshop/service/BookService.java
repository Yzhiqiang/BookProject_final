package com.book.bookshop.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.book.bookshop.entity.Book;
import com.book.bookshop.mapper.BookMapper;
import org.springframework.stereotype.Service;

/**
 * @Author:Yuzhiqiang
 * @Description:
 * @Date: Create in 22:41 2021/10/10
 * @Modified By:
 */
@Service
public class BookService extends ServiceImpl<BookMapper, Book> {
}
