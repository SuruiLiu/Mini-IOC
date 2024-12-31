# Mini-IOC-Container

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)
[![Java](https://img.shields.io/badge/Java-8%2B-orange.svg)](https://www.java.com)

一个轻量级的 IOC (Inversion of Control) 容器实现，用于深入理解依赖注入和控制反转的核心原理。本项目模拟了 Spring Framework 的核心功能，提供了一个简单但功能完整的 IOC 容器。

## 项目结构 

```
src/main/java/org/example/
├── annotation/
│   ├── Autowired.java    # 依赖注入注解
│   └── Component.java    # 组件标记注解
├── IOC.java              # IOC容器核心实现
├── User.java             # 示例实体类
├── UserRepository.java   # 数据访问层示例
├── UserService.java      # 业务逻辑层示例
└── IOCTest.java          # 测试类
```

## 核心特性

### 1. IOC容器实现
- 基于 `ConcurrentHashMap` 的 Bean 缓存
- 支持运行时动态加载类
- 自动扫描指定包下的组件

### 2. 注解支持
- `@Component`: 标记类为容器管理的组件
- `@Autowired`: 实现依赖自动注入

### 3. Bean生命周期管理
- 自动扫描和注册
- 懒加载机制
- 单例模式支持

## 快速开始

### 1. 定义组件
```java
@Component
public class UserRepository {
    public void save(User user) {
        System.out.println("Saving user: " + user);
    }
}

@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;
    
    public void createUser(String name, String email) {
        User user = new User(name, email);
        userRepository.save(user);
    }
}
```

### 2. 初始化容器
```java
IOC ioc = new IOC();
ioc.scan("org.example");
```

### 3. 获取并使用Bean
```java
UserService userService = (UserService) ioc.getBean("userService");
userService.createUser("张三", "zhangsan@example.com");
```

## 实现原理

### 1. 组件扫描
- 使用Java反射API扫描指定包下的所有类
- 识别带有 `@Component` 注解的类并注册
- 支持自定义Bean名称

### 2. 依赖注入
- 通过反射获取带有 `@Autowired` 注解的字段
- 自动注入依赖对象
- 支持字段级别的依赖注入

### 3. Bean管理
- 使用 `ConcurrentHashMap` 确保线程安全
- 实现单例模式
- 支持运行时动态创建对象

## 示例代码解析

### User实体类
```java
public class User {
    private String name;
    private String email;
    // getters and setters
}
```

### UserRepository数据访问层
```java
@Component
public class UserRepository {
    public void save(User user) {
        System.out.println("Saving user: " + user);
    }
    
    public User findById(Long id) {
        return new User("Test User", "test@example.com");
    }
}
```

## 构建要求

- JDK 8 或更高版本
- Maven 3.6.0 或更高版本

## 如何贡献

1. Fork 本仓库
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 提交 Pull Request

## 待优化特性

- [ ] 支持构造器注入
- [ ] 添加 Bean 生命周期回调
- [ ] 实现 AOP 支持
- [ ] 添加配置文件支持
- [ ] 完善单元测试

## 作者

Suu (Surui Liu)

## 许可证

本项目采用 MIT 许可证 - 详见 [LICENSE](LICENSE) 文件

## 致谢

感谢所有对此项目感兴趣的开发者。如果您觉得这个项目对您有帮助，请给个 star ⭐️！
```
