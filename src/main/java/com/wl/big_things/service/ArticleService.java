package com.wl.big_things.service;

import com.wl.big_things.pojo.Article;
import com.wl.big_things.pojo.PageBean;

public interface ArticleService {
    void add(Article article);

    PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state);

    void update(Integer id, String title, String content, String coverImg, String state, Integer categoryId);

    Article detail(Integer id);

    void delete(Integer id);
}
