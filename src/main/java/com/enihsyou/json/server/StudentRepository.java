package com.enihsyou.json.server;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/** DAO */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    /** 通过 {@link Student#number} 获取一个学生对象 */
    Student findByNumber(String number);

    /** 通过 {@link Student#name} 获取一个学生列表，因为一个名字可以对应多个学生 */
    List<Student> findByName(String name);
}
