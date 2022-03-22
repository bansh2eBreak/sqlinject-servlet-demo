# sqlinject-servlet-demo

边学java web边写写安全漏洞代码，希望可以坚持下去

安装教程：
1、复制资料中的静态页面到项目的webapp目录下
2、创建mybatis数据库，创建tb_user表，创建User实体类
3、导入MyBatis坐标，MySQL驱动坐标
4、创建mybatis-config.xml核心配置文件，UserMapper.xml映射文件，UserMapper接口

sql语句：

use mybatis;

-- 删除tb_user表

drop table if exists tb_user;

-- 创建tb_user表

create table tb_user(
	id int PRIMARY KEY auto_increment,
	username VARCHAR(20) UNIQUE,
	password VARCHAR(32)
);

-- 添加数据

INSERT INTO tb_user(username,password) VALUES('zhangsan', '123'),('lisi','456');

select * from tb_user;

可以直接打成war包放到tomcat中运行，也可以在IDEA集成tomcat插件直接运行。
