package com.example.json_demo;

import org.hibernate.annotations.NaturalId;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/** 学生表到JavaBean的映射 */
@Entity
public class Student {

    /** 主键 */
    @Id
    @GeneratedValue
    private Long id;

    /** 学号，添加了unique限制 */
    @NaturalId
    private String number;

    /** 姓名 */
    private String name;

    /** JavaBean需要一个无参数的构造器 */
    Student() {
    }

    Student(final String number, final String name) {
        this.number = number;
        this.name = name;
    }

    // fixme 一个JavaBean需要getter setter方法，需要实现equals hashCode方法
    // 注意，如果没有getter setter方法，json化会失败
    // 尝试使用IntelliJ IDEA的 `Alt+Insert (⌘+N、⌃+↩)` 快捷方法
}
