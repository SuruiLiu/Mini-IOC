package org.example;

/**
 * Author: Surui Liu
 * Date: 2024/12/31
 * Description: IOC容器的测试类
 * 演示了IOC容器的基本使用流程：
 * 1. 创建IOC容器
 * 2. 扫描指定包
 * 3. 获取Bean实例
 * 4. 执行业务方法
 */

public class IOCTest {
    public static void main(String[] args) {
        try {
            // 创建IOC容器
            IOC ioc = new IOC();

            // 扫描包
            ioc.scan("org.example");

            // 获取UserService,
            UserService userService = (UserService) ioc.getBean("userService");

            // 测试创建用户
            userService.createUser("张三", "zhangsan@example.com");

            // 测试查询用户
            System.out.println("查询结果：" + userService.getUserById(1L));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
