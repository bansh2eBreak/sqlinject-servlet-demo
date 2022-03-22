package com.nvyao.web;

import com.nvyao.mapper.UserMapper;
import com.nvyao.pojo.User;
import com.nvyao.util.SqlSessionFactoryUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/login2Servlet")
public class Login2Servlet extends HttpServlet {
    /**
     * 演示sql注入
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1、接受用户名和密码
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //2、调用Mybatis完成查询
        //2.1 获取SqlSessionFactory对象
        /*String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);*/
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtils.getSqlSessionFactory();
        //2.2 获取SqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //2.3 获取Mapper
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        //2.4 调用方法
        List<User> users = userMapper.selectAll(username, password);
//        System.out.println(users);
        //2.5 释放资源
        sqlSession.close();

        //获取对应的字符输出流，并设置content-type
        response.setContentType("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter();
        //3、判断users
        if(!users.isEmpty()){
            //登陆成功
            writer.write("登陆成功");
        }else{
            //登陆失败
            writer.write("登陆失败");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
