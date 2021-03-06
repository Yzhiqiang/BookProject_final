package com.len.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author zhuxiaomeng
 * @date 2018/11/23.
 * @email lenospmiller@gmail.com
 */
@Data
@ApiModel("简单文章")
public class SimpleArticle {

    private String code;

    private String title;
}
