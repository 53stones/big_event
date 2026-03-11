package com.wl.big_things.controller;

import com.wl.big_things.pojo.Category;
import com.wl.big_things.pojo.Result;
import com.wl.big_things.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @PostMapping
    public Result<Void> add(@RequestBody @Validated(Category.Add.class) Category category)
    {
        categoryService.add(category);
        return Result.success();
    }
    @GetMapping
    public Result<List<Category>> list()
    {
        List<Category> cg =categoryService.list();
        return Result.success(cg);
    }
    @GetMapping("/detail")
    public Result<Category> detail( Integer id)
    {
        Category cg= categoryService.findById(id);
        return Result.success(cg);
    }
    @PutMapping
    public Result<Void> update(@RequestBody @Validated(Category.Update.class) Category category)
    {
        categoryService.update(category);
        return Result.success();
    }
}
