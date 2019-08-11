package com.iyuedu.redis.service.impl;

import com.iyuedu.redis.domain.Category;
import com.iyuedu.redis.mapper.CategoryMapper;
import com.iyuedu.redis.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private RedisTemplate<Object, Object> redisTemlate;

    @Override
    public List<Category> getAllCategory() {

        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemlate.setKeySerializer(redisSerializer);

        List<Category> list = (List<Category>) redisTemlate.opsForValue().get("listCategory");
        if (null == list) {
                    System.out.println(System.currentTimeMillis()+"DB获得>>>>>>>>>>>>>>>>>>>>>>>");
                    list = categoryMapper.list();
                    redisTemlate.opsForValue().set("listCategory", list);

        }else{
            System.out.println(System.currentTimeMillis()+"缓存中获得>>>>>>>>>>>>>>>>>>>>>>>");
        }

//        if (null == list) {
//            synchronized (this){
//                list = (List<Category>) redisTemlate.opsForValue().get("listCategory");
//                if(null==list){
//                    System.out.println(System.currentTimeMillis()+"DB获得>>>>>>>>>>>>>>>>>>>>>>>");
//                    list = categoryMapper.list();
//                    redisTemlate.opsForValue().set("listCategory", list);
//                }else{
//                    System.out.println(System.currentTimeMillis()+"缓存中获得>>>>>>>>>>>>>>>>>>>>>>>");
//                }
//            }
//        }else{
//            System.out.println(System.currentTimeMillis()+"缓存中获得>>>>>>>>>>>>>>>>>>>>>>>");
//        }

        return list;
    }
}
