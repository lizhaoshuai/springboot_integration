package com.qianqiao.mapper;

import com.qianqiao.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface StudentMapper {
    public List<Student> findAll();
}
