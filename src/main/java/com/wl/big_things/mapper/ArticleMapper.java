package com.wl.big_things.mapper;

import com.wl.big_things.pojo.Article;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ArticleMapper {
    @Insert("insert into article(title,content,cover_img,state,category_id,create_user,create_time,update_time) "+
    "values(#{title},#{content},#{coverImg},#{state},#{categoryId},#{createUser},#{createTime},#{updateTime})")
    void add(Article article);

    List<Article> list(Integer userId, Integer categoryId, String state);
    @Update("update article set title=#{title},content=#{content},cover_img=#{coverImg},state=#{state},category_id=#{categoryId},update_time=now() where create_user=#{create_user} and id=#{id}")
    void update(Integer id, String title, String content, String coverImg, String state, Integer categoryId, Integer create_user);
    @Select("select * from article where id=#{id} and create_user=#{createUser}")
    Article detail(Integer id, Integer createUser);
    @Delete("delete from article where id=#{id} and create_user=#{createUser}")
    void delete(Integer id, Integer createUser);
}
