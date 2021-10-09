package com.len.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhuxiaomeng
 * @date 2018/10/14.
 * @email lenospmiller@gmail.com
 */
@Data
public class VArticle {

    private String title;

    private String content;

    private List<String> category=new ArrayList<>();

    private List<String> tags=new ArrayList<>();
}
