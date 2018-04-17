package com.enihsyou.json.server;

import org.hibernate.annotations.NaturalId;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Student student = (Student) o;
        return Objects.equals(id, student.id) &&
               Objects.equals(number, student.number) &&
               Objects.equals(name, student.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, number, name);
    }

    Long getId() {

        return id;
    }

    void setId(final Long id) {
        this.id = id;
    }

    String getNumber() {
        return number;
    }

    void setNumber(final String number) {
        this.number = number;
    }

    String getName() {
        return name;
    }

    void setName(final String name) {
        this.name = name;
    }
// fixme 一个JavaBean需要getter setter方法，需要实现equals hashCode方法
    // 注意，如果没有getter setter方法，json化会失败
    // 尝试使用IntelliJ IDEA的 `Alt+Insert (⌘+N、⌃+↩)` 快捷方法
}
