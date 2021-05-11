package com.hlliz.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hlliz.bean.Employee;

public interface EmployeeMapper {

    public Employee getEmployeeById(int id);

    public void addEmp(Employee employee);

    public Integer updateEmp(Employee employee);

    public void deleteEmp(Integer id);

    public Employee getByIdAndLastName(@Param("id") int id, @Param("lastName") String lastName);

    public Employee getEmployeeByMap(Map<String, Object> map);
}
