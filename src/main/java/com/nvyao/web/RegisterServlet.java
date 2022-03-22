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

@WebServlet("/registerServlet")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1、接受用户名和密码
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        //2、封装用户对象
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

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
        User u = userMapper.selectByUsername(username);

        //获取对应的字符输出流，并设置content-type
        response.setContentType("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter();

        //3、调用mapper 根据用户名查询用户对象，判断是否是已注册账号
        if(u == null){
            //说明用户名不存在，可以用
            userMapper.add(user);
            //提交事务
            sqlSession.commit();
            //释放资源
            sqlSession.close();
            writer.write("注册成功，请登陆！");
        }else{
            //不能添加用户，给出提示信息
            writer.write("用户名已存在!");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
