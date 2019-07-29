package com.qianqiao.repository;

import com.qianqiao.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student,Integer> {
    public List<Student> findAll();
}
