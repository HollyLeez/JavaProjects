package com.hlliz.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import com.hlliz.bean.Employee;

public interface EmployeeMapper {

    public Employee getEmployeeById(int id);

    public void addEmp(Employee employee);

    public Integer updateEmp(Employee employee);

    public void deleteEmp(Integer id);

    public Employee getByIdAndLastName(@Param("id") int id, @Param("lastName") String lastName);

    public Employee getEmployeeByMap(Map<String, Object> map);

    public List<Employee> getEmployeeList();

    public Map<String, Object> getEmpMapById(int id);

    //多条记录封装一个Map:Map<Integer, Employee> 键是这条记录的主键，值是封装后的java对象
    @MapKey("id")
    public Map<Integer, Employee> getEmpMap();
}
