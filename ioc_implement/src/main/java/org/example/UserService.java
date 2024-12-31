package org.example;

import org.example.annotation.Autowired;
import org.example.annotation.Component;

/**
 * Author: Surui Liu
 * Date: 2024/12/31
 * Description: 用户服务层，处理用户相关的业务逻辑
 * 通过@Component注解被IOC容器管理
 * 通过@Autowired注解注入UserRepository依赖
 * 提供用户创建和查询服务
 */

@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void createUser(String name, String email) {
        User user = new User(name, email);
        userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id);
    }
}