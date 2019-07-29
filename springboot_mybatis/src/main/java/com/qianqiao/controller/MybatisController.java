package com.qianqiao.controller;

import com.qianqiao.entity.Student;
import com.qianqiao.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MybatisController {

    @Autowired
    private StudentMapper studentMapper;

    @GetMapping("/findAll")
    public List<Student> findAll(){
        List<Student> students = studentMapper.findAll();
        return students;
    }

}
