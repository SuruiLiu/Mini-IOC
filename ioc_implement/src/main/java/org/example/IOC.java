package org.example;

import org.example.annotation.Autowired;
import org.example.annotation.Component;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Author: Surui Liu
 * Date: 2024/12/31
 * Description: 一个简单的IOC容器实现类，提供以下核心功能：
 * 1. 包扫描：扫描指定包下的所有类
 * 2. Bean管理：管理所有被@Component注解标记的类
 * 3. 依赖注入：通过@Autowired注解实现字段注入
 * 4. Bean实例化：负责创建和管理Bean的生命周期
 */

public class IOC {
    private Map<String, Object> beanMap = new ConcurrentHashMap<>();
    private Map<String, Class<?>> beanClassMap = new ConcurrentHashMap<>();

    public void scan(String basePackage) throws Exception {
        Set<Class<?>> classes = getClasses(basePackage);

        for(Class<?> clazz : classes) {
            if(clazz.isAnnotationPresent(Component.class)) {
                Component component = clazz.getAnnotation(Component.class); // 获取注解的实例
                String beanName = component.value();
                if (beanName.isEmpty()) {
                    beanName = toLowerFirstCase(clazz.getSimpleName());
                }
                beanClassMap.put(beanName, clazz);
            }
        }
    }

    public Object getBean(String beanName) throws Exception {
        Object bean = beanMap.get(beanName);
        if(bean != null) {
            return bean;
        }

        Class<?> beanClass = beanClassMap.get(beanName);
        if(beanClass == null) {
            throw new IllegalArgumentException("Cann't find beanClass : " + beanName);
        }

        bean = createBean(beanClass);
        beanMap.put(beanName, bean);
        return bean;
    }

    public Object createBean(Class<?> beanClass) throws Exception {
        Object bean = beanClass.getDeclaredConstructor().newInstance();
        // 对拿到的bean进行依赖注入它的字段，也可以使用setter方法来注入
        // 通过反射获取所有被 @Autowired 注解修饰的字段，并将相应的依赖注入到这些字段中
        for(Field field : beanClass.getDeclaredFields()){
            if(field.isAnnotationPresent(Autowired.class)){
                field.setAccessible(true);
                String fieldName = field.getName();
                Object dependency = getBean(fieldName);
                field.set(bean, dependency);
            }
        }
        return bean;
    }

    // 将类名首字母转小写
    private String toLowerFirstCase(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        char[] chars = str.toCharArray();
        chars[0] = Character.toLowerCase(chars[0]);
        return new String(chars);
    }

    private Set<Class<?>> getClasses(String basePackage) throws Exception {
        Set<Class<?>> classes = new HashSet<>();
        String path = basePackage.replace('.','/');

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> resources = classLoader.getResources(path);

        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            File directory = new File(resource.getFile());
            findClasses(directory, basePackage, classes);
        }

        return classes;
    }

    private void findClasses(File directory, String packageName, Set<Class<?>> classes) throws Exception {
        if (!directory.exists()) {
            return;
        }

        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    findClasses(file, packageName + "." + file.getName(), classes);
                } else if (file.getName().endsWith(".class")) {
                    String className = packageName + "." + file.getName().substring(0, file.getName().length() - 6);
                    classes.add(Class.forName(className));
                }
            }
        }
    }

}
