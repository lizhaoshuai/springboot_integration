### SpringBoot与其他技术的整合

#### 1、SpringBoot整合Mybatis

##### 1.1 pom.xml添加Mybaits依赖

``` xml
<!-- Mybaits依赖 -->
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>2.0.1</version>
</dependency>
```

##### 1.2 pom.xml添加数据库驱动依赖

```xml
<!-- MYSQL数据库连接驱动 -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
</dependency>
```

##### 1.3 添加数据库连接信息

在application.yml中添加数据库的连接信息

```yaml
# DB Configuration:
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/mytest?useUnicode=true&characterEncoding=utf8&autoReconnect=true&rewriteBatchedStatements=TRUE&serverTimezone=UTC
    username: root
    password: root
```

##### 1.4 创建student表

``` sql
-- -------------------------------
-- TABLE STRUCTURE FOR 'student'
-- -------------------------------
DROP TABLE IF EXISTS `student`;

CREATE TABLE `student` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`username` VARCHAR(50) DEFAULT NULL,
	`password` VARCHAR(50) DEFAULT NULL,
	`name` VARCHAR(50) DEFAULT NULL,
	PRIMARY KEY (`id`)
)ENGINE=INNODB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- -------------------------------
-- RECORDS OF student
-- -------------------------------
INSERT INTO `student` VALUES('1','zhangsan','123','张三');
INSERT INTO `student` VALUES('2','lisi','123','李四');
```

##### 1.5 创建实体Ben

``` java
@Data
public class Student {    
    private Integer id;
    private String username;
    private String password;
    private String name;
}
```

#####  1.6 编写Mapper

``` java
@Mapper
public interface StudentMapper {
    public List<Student> findAll();
}
```

##### 1.7 配置Mapper映射文件

``` xml
<?xml version="1.0" encoding="utf-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="com.qianqiao.mapper.StudentMapper">
	<select id="findAll" resultType="student">
		select * from student
	</select>
</mapper>
```

##### 1.8 在application.yml中添加Mybatis的信息

```yaml
# Mybatis配置信息
# Spring集成Mybatis环境
mybatis:
  # pojo别名扫描报
  type-aliases-package: com.qianqiao.entity
  # 加载Mybatis映射文件
  mapper-locations: classpath:mapper/*Mapper.xml
```

##### 1.9 编写测试Controller

```java
@RestController
public class MybatisController {

    @Autowired
    private StudentMapper studentMapper;

    @GetMapping("/findAll")
    public List<Student> findAll(){
        List<Student> students = studentMapper.findAll();
        return students;
    }
}
```

#### 2、SpringBoot整合junit

##### 2.1 pom.xml添加junit依赖

```xml
<!-- junit测试依赖 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```

##### 2.2 编写测试类

``` java
@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisTest {
    @Autowired
    private StudentMapper studentMapper;

    @Test
    public void findAll(){
        List<Student> students = studentMapper.findAll();
        System.out.println(students);
    }
}
```

#### 3、SpringBoot整合Spring Date JPA

##### 3.1 pom.xml添加Spring Date JPA依赖

```xml
<!--添加springdata-jpa依赖 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```

##### 3.2  pom.xml添加数据库驱动依赖

```xml
<!--添加MySQL驱动依赖 -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <scope>runtime</scope>
</dependency>
```

##### 3.3 在application.yml中配置数据库和jpa的相关属性

```yaml
# DB Configuration:
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/mytest?useUnicode=true&characterEncoding=utf8&autoReconnect=true&rewriteBatchedStatements=TRUE&serverTimezone=UTC
    username: root
    password: root
  # JPA Configuration:
  jpa:
    database: MySQL
    show-sql: true
    generate-ddl: true
    hibernate:
      ddl-auto: update
```

##### 3.4 创建和配置实体

```java
@Data
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //IDENTITY 自增
    private Integer id;
    private String username;
    private String password;
    private String name;
}
```

##### 3.5 编写StudentRepository

```java
public interface StudentRepository extends JpaRepository<Student,Integer> {
    public List<Student> findAll();
}
```

##### 3.6 编写测试类

``` java
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootJpaApplication.class)
public class JpaTests {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void test() {
        List<Student> students = studentRepository.findAll();
        System.out.println(students);
    }
}
```

#### 4、SpringBoot整合Redis

##### 4.1 pom.xml添加Redis依赖

```xml
<!--添加Redis依赖 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

##### 4.2 编写测试类

```java
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootJpaApplication.class)
public class RedisTest {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void test() throws Exception{
        // 1、从Redis中获取数据（数据的形式为json字符串）
        String studentListJson = redisTemplate.boundValueOps("studentRepository.findAll").get();
        // 2、判断Redis中是否存在数据
        if (null == studentListJson){
            // 3、不存在从数据库中查询
            List<Student> students = studentRepository.findAll();
            // 4、将查询出来的数据存储到Redis缓存中
            ObjectMapper objectMapper = new ObjectMapper();
            studentListJson = objectMapper.writeValueAsString(students);
            redisTemplate.boundValueOps("studentRepository.findAll").set(studentListJson);

            System.out.println("=========从数据库中查询==========");
        }else {
            System.out.println("===========从Redis缓存中获取==========");
        }

        // 5、将数据打印到控制台
        System.out.println(studentListJson);
    }
}
```

