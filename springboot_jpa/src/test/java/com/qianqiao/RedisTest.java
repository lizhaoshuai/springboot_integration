package com.qianqiao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qianqiao.entity.Student;
import com.qianqiao.repository.StudentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootJpaApplication.class)
public class RedisTest {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void test() throws Exception{
        // 1、从Redis中获取数据（数据的形式为json字符串）
        String studentListJson = redisTemplate.boundValueOps("studentRepository.findAll").get();
        // 2、判断Redis中是否存在数据
        if (null == studentListJson){
            // 3、不存在从数据库中查询
            List<Student> students = studentRepository.findAll();
            // 4、将查询出来的数据存储到Redis缓存中
            ObjectMapper objectMapper = new ObjectMapper();
            studentListJson = objectMapper.writeValueAsString(students);
            redisTemplate.boundValueOps("studentRepository.findAll").set(studentListJson);

            System.out.println("=========从数据库中查询==========");
        }else {
            System.out.println("===========从Redis缓存中获取==========");
        }

        // 5、将数据打印到控制台
        System.out.println(studentListJson);
    }
}
