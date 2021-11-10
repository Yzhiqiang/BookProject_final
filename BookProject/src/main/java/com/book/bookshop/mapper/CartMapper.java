package com.book.bookshop.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.book.bookshop.entity.Cart;
import com.book.bookshop.entity.CartVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author:Yuzhiqiang
 * @Description:
 * @Date: Create in 18:24 2021/10/12
 * @Modified By:
 */

@Mapper
@Repository
public interface CartMapper extends BaseMapper<Cart> {
    //根据用户查询购物车
    @Select("select bsc.*, bb.name AS bookName, bb.img_url AS img_Url, bb.new_price AS new_price\n" +
            "from bs_cart bsc\n" +
            "    join bs_book bb\n" +
            "        on bsc.book_id = bb.id\n" +
            "where bsc.user_id = #{userId}")
    List<CartVo> findCartListByUserid(Integer userId);



    @Select("<script>"+
                "select bsc.*, bb.name AS bookName, bb.img_url AS img_Url, bb.new_price AS new_price\n" +
                "from bs_cart bsc\n" +
                "    join bs_book bb\n" +
                "        on bsc.book_id = bb.id\n" +
                "where bsc.id in\n"+
                "<foreach item='item' collection='ids' open='(' separator=',' close=')' >"+
                    "#{item}"+
                "</foreach>"+
            "</script>"
    )
    List<CartVo> findCartListByIds(@Param("ids") List<String> ids);
}
