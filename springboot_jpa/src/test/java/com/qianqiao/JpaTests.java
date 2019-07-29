package com.qianqiao;

import com.qianqiao.entity.Student;
import com.qianqiao.repository.StudentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootJpaApplication.class)
public class JpaTests {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void test() {
        List<Student> students = studentRepository.findAll();
        System.out.println(students);
    }
}
