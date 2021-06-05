# 工程简介
> web实验结课作业的后端部分   

项目使用：
* springboot + mybatis 
* hutool...

## run
properties文件没有添加，如果需要看这里：
```properties
# 应用名称
spring.application.name=web-bk-code
# 应用服务 WEB 访问端口
server.port=****
#下面这些内容是为了让MyBatis映射
#指定Mybatis的Mapper文件
mybatis.mapper-locations=classpath:mappers/*xml
#指定Mybatis的实体目录
mybatis.type-aliases-package=***.***
# 数据库驱动：
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
# 数据源名称
spring.datasource.name=defaultDataSource
# 数据库连接地址
spring.datasource.url=jdbc:mysql://localhost:**port**/**ur_data_base**?serverTimezone=UTC
# 数据库用户名&密码：
spring.datasource.username=*****
spring.datasource.password=*****
```

MySQL_DDL
```mysql
create table user
(
	username varchar(200) not null,
	password varchar(32) not null,
	picUrl varchar(400) null,
	role varchar(20) not null,
	constraint user_username_uindex
		unique (username)
);
```