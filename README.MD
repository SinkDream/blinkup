# 说明
基于Tomcat的简单web框架

## 处理流程
在tomcat加载完成后自动装配
## TODO
* 数据库查询缓存
* 自定义线程池
* controller单例化
* 配置文件隔离及分组
* yaml配置文件支持

## 使用手册
需要在resources目录下创建config.properties文件
文件支持的字段

* db.driver
* db.url
* db.username
* db.password
* path.package
* path.jsp
* path.asset

Controller接口类使用@Controller注解

