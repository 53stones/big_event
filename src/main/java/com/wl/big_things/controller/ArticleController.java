package com.wl.big_things.controller;

import com.wl.big_things.pojo.Article;
import com.wl.big_things.pojo.Result;
import com.wl.big_things.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @PostMapping("/add")
    public Result<Void> add(@RequestBody Article article)
    {
        articleService.add(article);
        return Result.success();
    }
}
