package com.wl.big_things.service.impl;

import com.wl.big_things.mapper.CategoryMapper;
import com.wl.big_things.pojo.Category;
import com.wl.big_things.service.CategoryService;
import com.wl.big_things.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public void add(Category category) {
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        Map<String,Object> map=ThreadLocalUtil.get();
        Integer uid=(Integer) map.get("id");
        category.setCreateUser(uid);
        categoryMapper.add(category);
    }
}
