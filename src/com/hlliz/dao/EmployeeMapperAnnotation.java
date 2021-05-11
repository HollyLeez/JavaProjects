package com.hlliz.dao;

import org.apache.ibatis.annotations.Select;

import com.hlliz.bean.Employee;

public interface EmployeeMapperAnnotation {
    @Select("select * from tbl_employee where id=#{id}")
    public Employee getEmployeeById(int id);
}
