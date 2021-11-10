package com.book.bookshop;

import com.book.bookshop.mapper.CartMapper;
import com.book.bookshop.mapper.OrderMapper;
import com.book.bookshop.service.BookService;
import com.book.bookshop.utils.OrderUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BookProjectApplicationTests {
    @Autowired
    BookService bookService;

    @Autowired
    CartMapper cartMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Test
    void contextLoads() {
        bookService.list().forEach(System.out::println);
    }

    @Test
    void findCartList() {
        cartMapper.findCartListByUserid(5).forEach(System.out::println);
    }



    @Test
    void testCreateOrderNo() {
        String str = OrderUtils.createOrderNum();
        String str2 = OrderUtils.createOrderNum();
        System.out.println(str + " "+ str2);
        System.out.println(OrderUtils.createOrderNum());
    }

}
