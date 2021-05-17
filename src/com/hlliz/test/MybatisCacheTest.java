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
 * һ�����棨���ػ��棩�������ݿ�ͬһ�λỰ�ڼ��ѯ�������ݻ���ڱ��ػ����У�
 *                     �Ժ������Ҫ��ȡ��ͬ�����ݣ�ֱ�Ӵӻ������ã�û��Ҫ��ȥ��ѯ���ݿ⡣
 */
public class MybatisCacheTest {
    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        return new SqlSessionFactoryBuilder().build(inputStream);
    }
    @Test
    /**
     * һ�����棨���ػ��棩�������ݿ�ͬһ�λỰ�ڼ��ѯ�������ݻ���ڱ��ػ����У�
     *                      �Ժ������Ҫ��ȡ��ͬ�����ݣ�ֱ�Ӵӻ������ã�û��Ҫ��ȥ��ѯ���ݿ⡣
     * sqlSession����Ļ��棬��һֱ�����ģ�ÿ��sqlSesssion����ӵ���Լ���һ�����棬
     * ��ʵ����sqlSession�е�һ��map���´��ٲ��ȿ�map����û��
     * һ������ʧЧ�����������Ҫ�������ݿⷢ����ѯ��
     * 1.sqlSession��ͬ
     * 2.sqlSession��ͬ����ѯ������ͬ(��ǰһ�������л�û����������)
     * 3.sqlSession��ͬ�����β�ѯ�ڼ�ִ������ɾ�Ĳ����������ɾ�Ŀ��ܻ������������Ӱ�죩
     * 4.sqlSeesion��ͬ���ֶ������һ�����档
     */
    public void testFirstLevelCache1(){
        SqlSession sqlSession = null;
        try {
            SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
            sqlSession = sqlSessionFactory.openSession();
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
            Map<String, Object> emp1 = mapper.getEmpMapById(1);
            System.out.println(emp1);
            //��ɾ��
//            mapper.addEmp(new Employee(null,"Oil","0","xxx@xx.com"));
//            sqlSession.commit();
            //�������
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
     * �������棨ȫ�ֻ��棩������namespace����Ļ��棺һ��namespace��Ӧһ����������
     * �������ƣ�
     * 1��һ���Ự����ѯһ�����ݣ�������ݾͻᱻ���ڵ�ǰ�Ự��һ��������
     * 2������Ự�رգ�һ�������е����ݻᱻ���浽���������У��µĻỰ��ѯ��Ϣ�Ϳ��Բ��ն��������е�����
     * 3�����sqlSession�м���EmployeeMapper��õ�Employee��������DepartmentMapper��õ�Department����
     *    ��ͬ��namespace��������ݻᱻ���ڶ�Ӧ�Ļ��棨map����
     *    ���������Ĭ�϶����ȱ�����һ�������У�ֻ�лỰ�ύ��رպ�һ����������ݲŻ�ת�Ƶ�����������
     *
     *  ��������ʹ�ò��裺
     *  a������ȫ�ֶ�������
     *  b��ȥmapper.xml������ʹ�ö�������
     *  c��pojo��Ҫʵ�����л��ӿ�
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
            //�ڶ��λ����ǴӶ����������õ��ģ���û�з���sql
            Map<String, Object> emp2 = mapper2.getEmpMapById(1);
            sqlSession2.close();


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            sqlSession.close();
        }
    }
}
