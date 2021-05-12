package com.hlliz.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.hlliz.bean.Employee;
import com.hlliz.dao.EmployeeMapper;
import com.hlliz.dao.EmployeeMapperAnnotation;
import com.hlliz.dao.EmployeeMapperDynamicSQL;

public class MybatisTest {
    @Test
    public void test() throws IOException {
        //根据全局配置文件创建一个SqlSessionFactory对象
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        //获取sqlSession实例，能直接执行已经映射的sql语句
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            Employee employee = sqlSession.selectOne("com.hlliz.mybatis.EmployeeMapper.selectEmp", 1);
            System.out.println(employee);
        } finally {
            sqlSession.close();
        }

    }
    @Test
    public void test01() throws IOException {
        //获取SqlsessionFactory
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        //获取SqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //获取接口实现类对象
        try {
            //mybatis会为接口自动创建一个代理对象，去执行增删改查方法
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
            Employee employee = mapper.getEmployeeById(1);
            System.out.println(mapper.getClass());
            System.out.println(employee);
        } finally {
            sqlSession.close();
        }

    }

    /**
     * 基于注解，不写Mapper映射文件
     * @throws IOException
     */
    @Test
    public void test02() throws IOException {
        //获取SqlsessionFactory
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        //获取SqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //获取接口实现类对象
        try {
            //mybatis会为接口自动创建一个代理对象，去执行增删改查方法
            EmployeeMapperAnnotation mapper = sqlSession.getMapper(EmployeeMapperAnnotation.class);
            Employee employee = mapper.getEmployeeById(1);
            System.out.println(mapper.getClass());
            System.out.println(employee);
        } finally {
            sqlSession.close();
        }

    }
    @Test
    public void testAddEmp(){
        SqlSession sqlSession = null;
        try {
            SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
            //获取到的sqlSession不会自动提交数据
            sqlSession = sqlSessionFactory.openSession();
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
            Employee employee = new Employee(null,"Tom","1","wajijiwa@126.com");
            mapper.addEmp(employee);
            System.out.println(employee.getId());
            sqlSession.commit();
        } catch (IOException e) {
            e.printStackTrace();
            sqlSession.rollback();
        } finally {
            if(sqlSession!=null){
                sqlSession.close();
            }
        }
    }

    @Test
    public void testupdateEmp(){
        SqlSession sqlSession = null;
        try {
            SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
            //获取到的sqlSession不会自动提交数据
            sqlSession = sqlSessionFactory.openSession();
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
            Integer count = mapper.updateEmp(new Employee(1, "jerry", "0", "tom@126.com"));
            System.out.println(count+"条数据被更新");
            sqlSession.commit();
        } catch (IOException e) {
            e.printStackTrace();
            sqlSession.rollback();
        } finally {
            if(sqlSession!=null){
                sqlSession.close();
            }
        }
    }
    @Test
    public void testDeleteEmp(){
        SqlSession sqlSession = null;
        try {
            SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
            //获取到的sqlSession不会自动提交数据
            sqlSession = sqlSessionFactory.openSession();
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
            mapper.deleteEmp(3);
            sqlSession.commit();
        } catch (IOException e) {
            e.printStackTrace();
            sqlSession.rollback();
        } finally {
            if(sqlSession!=null){
                sqlSession.close();
            }
        }
    }

    @Test
    public void testIdAndLastName(){
        SqlSession sqlSession = null;
        try {
            SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
            //获取到的sqlSession不会自动提交数据
            sqlSession = sqlSessionFactory.openSession();
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
            Employee employee = mapper.getByIdAndLastName(4, "Tom");
            System.out.println(employee);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(sqlSession!=null){
                sqlSession.close();
            }
        }
    }

    @Test
    public void testGetEmployeeByMap(){
        SqlSession sqlSession = null;
        try {
            SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
            //获取到的sqlSession不会自动提交数据
            sqlSession = sqlSessionFactory.openSession();
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
            Map<String, Object> map = new HashMap<>();
            map.put("id", 4);
            map.put("lastName", "Tom");
            Employee employee = mapper.getEmployeeByMap(map);
            System.out.println(employee);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(sqlSession!=null){
                sqlSession.close();
            }
        }
    }
    @Test
    public void testgetList(){
        SqlSession sqlSession = null;
        try {
            SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
            //获取到的sqlSession不会自动提交数据
            sqlSession = sqlSessionFactory.openSession();
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
            List<Employee> list = mapper.getEmployeeList();
            for (Employee employee : list) {
                System.out.println(employee);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(sqlSession!=null){
                sqlSession.close();
            }
        }
    }

    @Test
    public void testgetMap(){
        SqlSession sqlSession = null;
        try {
            SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
            //获取到的sqlSession不会自动提交数据
            sqlSession = sqlSessionFactory.openSession();
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
            Map<String, Object> map = mapper.getEmpMapById(4);
            System.out.println(map);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(sqlSession!=null){
                sqlSession.close();
            }
        }
    }
    @Test
    public void testgetMap2(){
        SqlSession sqlSession = null;
        try {
            SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
            //获取到的sqlSession不会自动提交数据
            sqlSession = sqlSessionFactory.openSession();
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
            Map<Integer, Employee> map = mapper.getEmpMap();
            System.out.println(map);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(sqlSession!=null){
                sqlSession.close();
            }
        }
    }

    @Test
    public void testGetByCondition(){
        SqlSession sqlSession = null;
        try {
            SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
            //获取到的sqlSession不会自动提交数据
            sqlSession = sqlSessionFactory.openSession();
            EmployeeMapperDynamicSQL mapper = sqlSession.getMapper(EmployeeMapperDynamicSQL.class);
            List<Employee> list = new ArrayList<>();
            Employee emp1 = new Employee(null, "Smith","1","smith@qq.com");
            Employee emp2 = new Employee(null, "Willian","1","willian@istst.com");
            list.add(emp1);
            list.add(emp2);
            mapper.addEmps(list);
            sqlSession.commit();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(sqlSession!=null){
                sqlSession.close();
            }
        }
    }
    public  SqlSessionFactory getSqlSessionFactory() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        return new SqlSessionFactoryBuilder().build(inputStream);
    }
}
