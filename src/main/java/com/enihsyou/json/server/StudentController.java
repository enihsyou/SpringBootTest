package com.enihsyou.json.server;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*参考资料：https://spring.io/guides/gs/rest-service/*/
@RestController
public class StudentController {

    @Autowired
    public StudentController(final StudentRepository studentRepository) {this.studentRepository = studentRepository;}

    /**
     * 引用一个数据库访问器(DAO)，直接使用就行，不需要new
     * 这个仓库具有很多操作数据库的快捷方法
     */
    private final StudentRepository studentRepository;

    /** 创建一个Gson对象 */
    private Gson gson = new Gson(); // todo 可以调教这个对象，使他输出的字符串是格式化后的，具有缩进格式

    /**
     * todo 实现这个方法，使他能接受来自http://localhost:10888/json 的GET请求
     *
     * @return 数据库里所有学生的列表Json字符串
     */
    @GetMapping(value = "/json", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String listStudentsUsingGson() {

        return ""; // todo
    }

    /**
     * todo 实现这个方法，使他能接受来自http://localhost:10888/json 的POST请求
     * 如果遇到传来的对象具有重复的学生学号，不应该添加到数据库里
     *
     * @param studentString 需要创建的学生的Json字符串，格式如
     *                      {@code {"number":"114","name":"name2"}}
     *
     * @return 数据库里所有学生的列表Json字符串
     */
    @PostMapping(value = "/json", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String createStudentsUsingGson(@RequestBody String studentString) {

        return ""; // todo
    }

    //
    // 已经写好的快捷方法
    //

    /**
     * GET http://localhost:10888/students
     *
     * @return 数据库里的学生对象列表
     */
    @GetMapping("/students")
    public List<Student> listStudents() {
        return studentRepository.findAll();
    }

    /**
     * GET http://localhost:10888/students?name=name2
     *
     * @param name 搜索名叫name的学生列表
     *
     * @return 用name做搜索参数的结果
     */
    @GetMapping(value = "/students", params = "name")
    public List<Student> findStudent(@RequestParam String name) {
        return studentRepository.findByName(name);
    }

    /**
     * POST http://localhost:10888/students
     * Content-Type: application/json
     *
     * {
     * "number":"114",
     * "name":"name2"
     * }
     *
     * @param student 需要创建的学生对象
     *
     * @return 当前数据库中的学生列表
     */
    @PostMapping("/students")
    public List<Student> createStudent(@RequestBody Student student) {
        // fixme 如果传来的[student.number]是重复的，数据库会抛出错误，需要解决这个问题
        studentRepository.save(student);
        return listStudents();
    }

    /**
     * GET http://localhost:10888/students/number/114
     *
     * 获取学号是number的学生信息
     *
     * @param number 学生学号
     *
     * @return 一个学生对象的Json字符串
     */
    @GetMapping("/students/number/{number}")
    public Student detailStudent(@PathVariable String number) {
        return studentRepository.findByNumber(number);
    }
}
