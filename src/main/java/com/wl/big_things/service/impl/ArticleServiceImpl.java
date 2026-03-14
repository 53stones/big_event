package com.wl.big_things.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wl.big_things.mapper.ArticleMapper;
import com.wl.big_things.pojo.Article;
import com.wl.big_things.pojo.PageBean;
import com.wl.big_things.service.ArticleService;
import com.wl.big_things.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public void add(Article article) {
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());
        Map<String,Object> map=ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        article.setCreateUser(id);
        articleMapper.add(article);
    }

    @Override
    public PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state) {
        PageBean<Article> pb = new PageBean<>();
        PageHelper.startPage(pageNum, pageSize);
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        List<Article> as = articleMapper.list(userId, categoryId, state);
        Page<Article> p = (Page<Article>) as;
        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());
        return pb;
    }

    @Override
    public void update(Integer id, String title, String content, String coverImg, String state, Integer categoryId) {
        Map<String,Object>map=ThreadLocalUtil.get();
        Integer create_user=(Integer)map.get("id");
        articleMapper.update(id,title,content,coverImg,state,categoryId,create_user);
    }

    @Override
    public Article detail(Integer id) {
        Map<String,Object>map=ThreadLocalUtil.get();
        Integer create_user=(Integer)map.get("id");
        return articleMapper.detail(id,create_user);
    }

    @Override
    public void delete(Integer id) {
        Map<String,Object>map=ThreadLocalUtil.get();
        Integer create_user=(Integer)map.get("id");
        articleMapper.delete(id,create_user);
    }
}
