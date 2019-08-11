package com.iyuedu.redis.mapper;

import com.iyuedu.redis.domain.Category;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

@Mapper
public interface CategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Integer id);

    List<Category> list();

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);
}