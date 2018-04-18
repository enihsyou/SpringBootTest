package com.enihsyou.json.server;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/** DAO，不需要自己实现，框架会替我们完成实现接口的工作 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    /** 通过 {@link Student#number} 获取一个学生对象 */
    Student findByNumber(String number);

    /** 通过 {@link Student#name} 获取一个学生列表，因为一个名字可以对应多个学生 */
    List<Student> findByName(String name);

    /** 获取数据库里全部的 {@link Student}对象 */
    @Override
    List<Student> findAll();

    /** 保存一个{@link Student}对象到数据库中 */
    @Override
    <S extends Student> S save(S entity);
}
