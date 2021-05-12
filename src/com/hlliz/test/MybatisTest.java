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
        //����ȫ�������ļ�����һ��SqlSessionFactory����
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        //��ȡsqlSessionʵ������ֱ��ִ���Ѿ�ӳ���sql���
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
        //��ȡSqlsessionFactory
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        //��ȡSqlSession����
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //��ȡ�ӿ�ʵ�������
        try {
            //mybatis��Ϊ�ӿ��Զ�����һ���������ȥִ����ɾ�Ĳ鷽��
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
            Employee employee = mapper.getEmployeeById(1);
            System.out.println(mapper.getClass());
            System.out.println(employee);
        } finally {
            sqlSession.close();
        }

    }

    /**
     * ����ע�⣬��дMapperӳ���ļ�
     * @throws IOException
     */
    @Test
    public void test02() throws IOException {
        //��ȡSqlsessionFactory
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        //��ȡSqlSession����
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //��ȡ�ӿ�ʵ�������
        try {
            //mybatis��Ϊ�ӿ��Զ�����һ���������ȥִ����ɾ�Ĳ鷽��
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
            //��ȡ����sqlSession�����Զ��ύ����
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
            //��ȡ����sqlSession�����Զ��ύ����
            sqlSession = sqlSessionFactory.openSession();
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
            Integer count = mapper.updateEmp(new Employee(1, "jerry", "0", "tom@126.com"));
            System.out.println(count+"�����ݱ�����");
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
            //��ȡ����sqlSession�����Զ��ύ����
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
            //��ȡ����sqlSession�����Զ��ύ����
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
            //��ȡ����sqlSession�����Զ��ύ����
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
            //��ȡ����sqlSession�����Զ��ύ����
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
            //��ȡ����sqlSession�����Զ��ύ����
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
            //��ȡ����sqlSession�����Զ��ύ����
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
            //��ȡ����sqlSession�����Զ��ύ����
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
