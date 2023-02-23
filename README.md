# userCenter-back
用户中心项目 version1
# 后端技术支持
① SpringBoot 
② Spring/SpringMvc
③ Mysql
④ MybatisPlus
# 项目搭建流程
## 后端初始化
1. 准备环境

   1. mysql环境
   2. mybaits-plus

2. 初始化java项目

   - 可以去GitHub上下载模板（但如果是初学，不建议使用，质量参差不齐）
   - springboot官方的模板
   - idea直接生成

3. 配置依赖(mybatis-plus)
  
    <!-- https://mvnrepository.com/artifact/com.baomidou/mybatis-plus-boot-starter -->
    <dependency>
        <groupId>com.baomidou</groupId>
        <artifactId>mybatis-plus-boot-starter</artifactId>
        <version>3.3.1</version>
    </dependency>
   
    <!-- https://mvnrepository.com/artifact/com.baomidou/mybatis-plus-generator 生成器依赖-->
    <dependency>
        <groupId>com.baomidou</groupId>
        <artifactId>mybatis-plus-generator</artifactId>
        <version>3.3.1</version>
    </dependency>
    <!--模板依赖-->
    <dependency>
        <groupId>org.apache.velocity</groupId>
        <artifactId>velocity-engine-core</artifactId>
        <version>2.1</version>
    </dependency>
    <!--mybatis-plus逆向功能所需的模板引擎-->
    <dependency>
        <groupId>org.freemarker</groupId>
        <artifactId>freemarker</artifactId>
        <version>2.3.30</version>
    </dependency>
<!--        SQL分析打印-->
        <dependency>
            <groupId>p6spy</groupId>
            <artifactId>p6spy</artifactId>
            <version>3.9.1</version>
        </dependency>
 4.数据库配置 ---源码中
 
## 数据库表设计
- ids主键 每个人都有唯一的标识 varchar （不可为空，默认自增）
- nickName 昵称 varchar （可为空）
- avatar 头像路径 varchar （可为空）
- gender 性别 tinyint （可为空）
- ~~account~~ userAccount 账号 varchar  **account** 在mysql5.7中是关键字，尽量不使用关键字来作为字段（虽然用反引号包围就可以了） （不可为空）
- password 密码 varchar  （不可为空）
- phone 电话 varchar （可为空）
- email 邮箱 varchar （可为空）
- userStatus 是否有效(被封号之类) int    0-正常 （有默认，不可为空）

- createTime 创建时间 localDate/datetime  （默认值）

- updateTime 更新时间 localDate /datetime  （默认值）
<!--创建的时候可以直接为时间指定默认并且随着记录改变跟着改变，当然也可以用后面的mysqlbatis自动填充功能-->
 createTime   datetime default CURRENT_TIMESTAMP null comment '创建时间',
 updateTime   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
       
 ## 插件安利
  ① 数据库对应实体类，和数据库字段相联系。自动生成：使用插件 **MybatisX** 自动生成代码 ----
  
  ② 当创建一个pojo类时，一般指定一个序列化数为了序列化安全，可以使用插件 **GenerateSerialVersionUID** 自动生成
  
  ③ 一键生成 对象的所有set方法并且配上默认初始值，插件 **GenerateAllSetter**
  
  更多的插件可以前往b站看鱼皮视频： https://www.bilibili.com/video/BV1yb4y1a7Aq/?spm_id_from=333.788&vd_source=eb83af48877ec21cc6becae65c4758a9
  
  好了，本项目的后端搭建并不复杂，逻辑实现就更不复杂了。基本就是什么开发规范，全局异常处理器，自定义错误码，自定义异常类等等
  
  ----- 未完善，待后续完善....目前 version0.1
   
