package com.wl.big_things.controller;

import com.wl.big_things.pojo.Article;
import com.wl.big_things.pojo.PageBean;
import com.wl.big_things.pojo.Result;
import com.wl.big_things.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @PostMapping("/add")
    public Result<Void> add(@RequestBody @Validated  Article article)
    {
        articleService.add(article);
        return Result.success();
    }
    @GetMapping
    public Result<PageBean<Article>>list(Integer PageNum, Integer PageSize
            , @RequestParam(required = false) Integer categoryId, @RequestParam(required = false)String state)
    {
        PageBean<Article> pb =articleService.list(PageNum,PageSize,categoryId,state);
        return Result.success(pb);
    }
    @PutMapping
    public Result<Void> update(Integer id,String title,String content,String coverImg,String state,Integer categoryId)
    {
        articleService.update(id,title,content,coverImg,state,categoryId);
        return Result.success();
    }
    @GetMapping("/detail")
    public Result<Article> detail(Integer id)
    {
        Article ar=articleService.detail(id);
        return Result.success(ar);
    }
    @DeleteMapping
    public Result<Void> delete(Integer id)
    {
        articleService.delete(id);
        return Result.success();
    }
}
