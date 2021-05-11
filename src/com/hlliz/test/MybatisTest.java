package com.hlliz.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.hlliz.bean.Employee;
import com.hlliz.dao.EmployeeMapper;
import com.hlliz.dao.EmployeeMapperAnnotation;

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
    public  SqlSessionFactory getSqlSessionFactory() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        return new SqlSessionFactoryBuilder().build(inputStream);
    }
}
