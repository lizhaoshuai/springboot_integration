package com.qianqiao;

import com.qianqiao.entity.Student;
import com.qianqiao.mapper.StudentMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootMybaitsApplication.class)
public class MybatisTest {
    @Autowired
    private StudentMapper studentMapper;

    @Test
    public void findAll(){
        List<Student> students = studentMapper.findAll();
        System.out.println(students);
    }
}
