package org.example;

import org.example.annotation.Component;

/**
 * Author: Surui Liu
 * Date: 2024/12/31
 * Description: 用户数据访问层，模拟数据库操作
 * 使用@Component注解标记为IOC容器管理的Bean
 * 提供用户的保存和查询功能（当前为模拟实现）
 */

@Component
public class UserRepository {
    public void save(User user) {
        System.out.println("Saving user to database: " + user);
    }

    public User findById(Long id) {
        System.out.println("Finding user by id: " + id);
        return new User("Test User", "test@example.com");
    }
}