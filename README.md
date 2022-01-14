# dubbo-basic-framework-plus

微服务基础框架搭建（升级版）

技术结构

    SpringBoot
    Dubbo
    Zoopeeker
    Redis
    Mybatis Plus

项目结构

    interface(接口核心模块)
    provider(供应者)
      user-provider(用户供应者)
    consumer(消费者)
      user-consumer(用户消费者)  

启动

    修改各服务中的appliction.yml配置，包括数据库连接,Zoopeeker连接,Redis连接等
    需启动redis客户端(集成一些技术需要用到redis)
    需启动Zoopeeker客户端(将服务注册到Zoopeeker服务中心中，版本3.6.3，可自行选择)
    Monitor监控中心服务:具有服务查询、服务治理的功能(可自行配置)
    启动类:各服务中Application应用启动类

swagger接口文档

    访问路径：http://localhost:port/doc.html
    根据自己项目所需的端口和命名方式配置appliction.yml配置中的值
