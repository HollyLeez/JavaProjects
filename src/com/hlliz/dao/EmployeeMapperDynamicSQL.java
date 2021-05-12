package com.hlliz.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hlliz.bean.Employee;

public interface EmployeeMapperDynamicSQL {

    public List<Employee> getEmpByCondition(Employee emp);

    public List<Employee> getEmpByChoose(Employee emp);

    public void updateEmp(Employee emp);

    public List<Employee> getEmpByForeach(Integer[] ids);

    public void addEmps(@Param("emps") List<Employee> emps);

}
