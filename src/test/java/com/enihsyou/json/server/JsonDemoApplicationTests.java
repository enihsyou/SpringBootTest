package com.enihsyou.json.server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/*https://spring.io/guides/gs/testing-web/*/
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@SuppressWarnings("NonAsciiCharacters")
public class JsonDemoApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private Gson gson = new Gson();

    /** 向http://localhost:$post/json 发送GET请求，获取全部的学生列表 */
    @Test
    public void 获取JSON格式的学生列表() {
        /*模拟客户端发送GET请求*/
        final ResponseEntity<String> responseEntity =
            this.restTemplate.getForEntity(String.format("http://localhost:%d/json", port), String.class);

        final HttpStatus statusCode = responseEntity.getStatusCode();
        final String entityBody = responseEntity.getBody();

        /*检查响应码是否正确*/
        assertThat(statusCode).isIn(httpStatusOk);

        /*检查返回体是否为空*/
        assertThat(entityBody).isNotEmpty();

        System.out.println("HTTP CODE = " + statusCode);
        System.out.println("HTTP HEADERS = " + responseEntity.getHeaders());
        System.out.println("HTTP BODY = " + entityBody);

        /*客户端进行String -> JavaBean的转换*/
        final List<Student> students = gson.fromJson(entityBody, new StudentListTypeToken().getType());
        System.out.println("students = " + students);
    }

    @Test
    @Repeat(3) // 重复发送
    public void 发送创建一个学生的请求() {
        /*生成随机的名字和学号*/
        final String randomStudentName = UUID.randomUUID().toString();
        final String randomStudentNumber = UUID.randomUUID().toString();
        /*客户端创建学生对象*/
        final Student student = new Student(randomStudentNumber, randomStudentName);
        System.out.println("student = " + student);

        /*客户端进行JavaBean -> String的转换*/
        final String json = gson.toJson(student);

        /*模拟客户端发送到服务器上*/
        final ResponseEntity<String> responseEntity =
            this.restTemplate.postForEntity(String.format("http://localhost:%d/json", port), json, String.class);

        final HttpStatus statusCode = responseEntity.getStatusCode();
        final String entityBody = responseEntity.getBody();

        /*检查响应码是否正确*/
        assertThat(statusCode).isIn(httpStatusOk);

        /*检查返回体是否为空*/
        assertThat(entityBody).isNotEmpty();

        System.out.println("HTTP CODE = " + statusCode);
        System.out.println("HTTP HEADERS = " + responseEntity.getHeaders());
        System.out.println("HTTP BODY = " + entityBody);

        /*模拟客户端进行String -> JavaBean的转换*/
        final List<Student> students = gson.fromJson(entityBody, new StudentListTypeToken().getType());
        System.out.println("students = " + students);

        assertThat(students).isNotEmpty();
        assertThat(students).extracting("number").contains(randomStudentNumber);
        assertThat(students).extracting("name").contains(randomStudentName);
    }

    @Test
    public void 重复发送创建一个学生的请求() {
        /*生成随机的名字和学号*/
        final String randomStudentName = UUID.randomUUID().toString();
        final String randomStudentNumber = UUID.randomUUID().toString();
        /*客户端创建学生对象*/
        final Student student = new Student(randomStudentNumber, randomStudentName);

        /*客户端进行JavaBean -> String的转换*/
        final String json = gson.toJson(student);

        /*模拟客户端发送到服务器上*/
        final ResponseEntity<String> post1 =
            this.restTemplate.postForEntity(String.format("http://localhost:%d/json", port), json, String.class);
        /*第一次发送需要成功*/
        assertThat(post1.getStatusCode()).isIn(httpStatusOk);

        /*第二次发送同样的请求*/
        final ResponseEntity<String> post2 =
            this.restTemplate.postForEntity(String.format("http://localhost:%d/json", port), json, String.class);

        final List<Student> students1 = gson.fromJson(post1.getBody(), new StudentListTypeToken().getType());
        System.out.println("students1 = " + students1);
        final List<Student> students2 = gson.fromJson(post2.getBody(), new StudentListTypeToken().getType());
        System.out.println("students2 = " + students2);

        /*重复的请求，不应该对数据库进行写入操作，两次应该返回一致的结果*/
        assertThat(students1).containsExactlyInAnyOrderElementsOf(students2);
    }

    private static class StudentListTypeToken extends TypeToken<List<Student>> { }

    static private List<HttpStatus> httpStatusOk = Arrays.asList(
        HttpStatus.OK,
        HttpStatus.CREATED,
        HttpStatus.ACCEPTED,
        HttpStatus.NO_CONTENT
    );
}
