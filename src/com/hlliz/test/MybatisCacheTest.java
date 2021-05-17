package com.hlliz.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.hlliz.bean.Employee;
import com.hlliz.dao.EmployeeMapper;

/**
 * 一级缓存（本地缓存）：与数据库同一次会话期间查询到的数据会放在本地缓存中，
 *                     以后如果需要获取相同的数据，直接从缓存中拿，没必要再去查询数据库。
 */
public class MybatisCacheTest {
    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        return new SqlSessionFactoryBuilder().build(inputStream);
    }
    @Test
    /**
     * 一级缓存（本地缓存）：与数据库同一次会话期间查询到的数据会放在本地缓存中，
     *                      以后如果需要获取相同的数据，直接从缓存中拿，没必要再去查询数据库。
     * sqlSession级别的缓存，是一直开启的，每个sqlSesssion对象拥有自己的一级缓存，
     * 其实就是sqlSession中的一个map，下次再查先看map中有没有
     * 一级缓存失效的情况（还需要再向数据库发出查询）
     * 1.sqlSession不同
     * 2.sqlSession相同，查询条件不同(当前一级缓存中还没有这条数据)
     * 3.sqlSession相同，两次查询期间执行了增删改操作（这次增删改可能会对这条数据有影响）
     * 4.sqlSeesion相同，手动清除了一级缓存。
     */
    public void testFirstLevelCache1(){
        SqlSession sqlSession = null;
        try {
            SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
            sqlSession = sqlSessionFactory.openSession();
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
            Map<String, Object> emp1 = mapper.getEmpMapById(1);
            System.out.println(emp1);
            //增删改
//            mapper.addEmp(new Employee(null,"Oil","0","xxx@xx.com"));
//            sqlSession.commit();
            //清除缓存
            sqlSession.clearCache();

            Map<String, Object> emp2 = mapper.getEmpMapById(1);
            System.out.println(emp2);
            System.out.println(emp1==emp2);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 二级缓存（全局缓存）：基于namespace级别的缓存：一个namespace对应一个二级缓存
     * 工作机制：
     * 1、一个会话，查询一套数据，这个数据就会被放在当前会话的一级缓存中
     * 2、如果会话关闭，一级缓存中的数据会被保存到二级缓存中，新的会话查询信息就可以参照二级缓存中的内容
     * 3、如果sqlSession中既有EmployeeMapper查得的Employee对象，又有DepartmentMapper查得的Department对象
     *    不同的namespace查出的数据会被放在对应的缓存（map）中
     *    查出的数据默认都会先被放在一级缓存中，只有会话提交或关闭后，一级缓存的数据才会转移到二级缓存中
     *
     *  二级缓存使用步骤：
     *  a、开启全局二级缓存
     *  b、去mapper.xml是配置使用二级缓存
     *  c、pojo需要实现序列化接口
     */
    @Test
    public void testFirstLevelCache2(){
        SqlSession sqlSession1 = null;
        SqlSession sqlSession2 = null;
        try {
            SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
            sqlSession1 = sqlSessionFactory.openSession();
            sqlSession2 = sqlSessionFactory.openSession();

            EmployeeMapper mapper1 = sqlSession1.getMapper(EmployeeMapper.class);
            EmployeeMapper mapper2 = sqlSession2.getMapper(EmployeeMapper.class);

            Map<String, Object> emp1 = mapper1.getEmpMapById(1);
            sqlSession1.close();
            //第二次缓存是从二级缓存中拿到的，并没有发送sql
            Map<String, Object> emp2 = mapper2.getEmpMapById(1);
            sqlSession2.close();


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            sqlSession.close();
        }
    }
}
