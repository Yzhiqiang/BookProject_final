package com.len.service;

import com.len.base.BaseService;
import com.len.entity.ArticleCategory;

import java.util.List;

/**
 * @author zhuxiaomeng
 * @date 2018/10/11.
 * @email lenospmiller@gmail.com
 */
public interface ArticleCategoryService extends BaseService<ArticleCategory, String> {

    void delByIds(List<String> ids);
}
