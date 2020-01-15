package com.iyuedu.redis.service.impl;

import com.iyuedu.redis.domain.Category;
import com.iyuedu.redis.mapper.CategoryMapper;
import com.iyuedu.redis.service.CategoryService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
	private static final Logger log = LoggerFactory.getLogger(CategoryService.class);
    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private RedisTemplate<Object, Object> redisTemlate;

    @Override
    public List<Category> getAllCategory() {

        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemlate.setKeySerializer(redisSerializer);

        List<Category> list = (List<Category>) redisTemlate.opsForValue().get("listCategory");
//        if (null == list) {
//                    System.out.println(System.currentTimeMillis()+"DB获得>>>>>>>>>>>>>>>>>>>>>>>");
//                    list = categoryMapper.list();
//                    redisTemlate.opsForValue().set("listCategory", list);
//
//        }else{
//            System.out.println(System.currentTimeMillis()+"缓存中获得>>>>>>>>>>>>>>>>>>>>>>>");
//        }

        if (null == list) {
            synchronized (this){
                list = (List<Category>) redisTemlate.opsForValue().get("listCategory");
                if(null==list){
                    log.debug(System.currentTimeMillis()+"DB获得>>>>>>>>>>>>>>>>>>>>>>>");
                    list = categoryMapper.list();
                    redisTemlate.opsForValue().set("listCategory", list);
                }else{
                    log.debug(System.currentTimeMillis()+"缓存中获得>>>>>>>>>>>>>>>>>>>>>>>");
                }
            }
        }else{
            log.debug(System.currentTimeMillis()+"缓存中获得>>>>>>>>>>>>>>>>>>>>>>>");
        }

        return list;
    }
}
