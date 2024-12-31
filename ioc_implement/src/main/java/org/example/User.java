package org.example;

/**
 * Author: Surui Liu
 * Date: 2024/12/31
 * Description: 用户实体类，用于存储用户基本信息
 * 包含用户名和邮箱信息，提供基本的getter/setter方法
 * 用于演示IOC容器的数据处理功能
 */

public class User {
    private String name;
    private String email;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{name='" + name + "', email='" + email + "'}";
    }
}
