# 你需要做什么

1. 使用IntelliJ IDEA或者Android Studio打开项目
![打开项目的方法](img/Snipaste_2018-04-17_16-54-26.png)
![打开项目的方法 Step.2](img/Snipaste_2018-04-17_16-57-11.png)

3. 去 [`Student`](src/main/java/com/enihsyou/json/server/Student.java#L9-L37) 类中，添加Getter Setter等JavaBean的方法

4. 去 [`StudentController`](src/main/java/com/enihsyou/json/server/StudentController.java#L15-L116) 类中，完成 `listStudentsUsingGson` 和 `createStudentsUsingGson` 这两个方法

5. 运行 [`JsonDemoApplicationTests`](src/test/java/com/enihsyou/json/server/JsonDemoApplicationTests.java#L23-L139) 进行测试。
IntelliJ IDEA的操作步骤如下，Android Studio的只需要点Run就行。
![运行测试的方法](img/Snipaste_2018-04-17_16-31-37.png)

6. [`test.http`](src/main/java/com/enihsyou/json/server/test.http) 是个请求的模拟发送器

期望同学们能得到<span style="color:#5c9b4f">All Green</span>的正确结果:smiley:



~~参考答案请切换到 *answer* 分支~~
