#### spring-boot-starter-freemarker（模板渲染工具）

创建时间：`2024年01月27日 21:28:00`

核心逻辑：`使用数据填充模板，获取字符串`

---

1. 作为HTML模板
2. 作为FDF模板（`基于HTML`）
3. 作为XML模板
4. 作为Excel模板（`基于XML`）
5. 作为TXT模板

---

```xml
<!-- 版本号与SpringBootStarter保持一致，基础freemarker版本不变 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-freemarker</artifactId>
    <version>2.7.18</version>
</dependency>
```



```yaml
spring:
  freemarker:
    template-loader-path: classpath:/ftl/  # 默认classpath:/templates/
    #suffix: .ftl  # 这里不需要额外指定，注入Configuration可手动指定文件名
    #enabled: true
    #cache: true
    #charset: UTF-8
    #check-template-location: true
```



```java
/**
 * 使用FreeMarker输出指定格式的TXT文本
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final Configuration freemarkerConfiguration;

    @PostMapping("/test01")
    public void test01(HttpServletResponse response) throws IOException, TemplateException {
        // 1.设置响应头
        // 关于Content-Disposition -> https://developer.mozilla.org/zh-CN/docs/web/http/headers/content-disposition
        // 关于Content-Type        -> https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Headers/Content-Type
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, " attachment; filename=userInfo.txt");
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);

        // 2.准备数据模型
        Map<String, Object> model = new HashMap<>();
        List<User> userList = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setName("Name-" + RandomStringUtils.randomAlphanumeric(7));
            user.setAge(ThreadLocalRandom.current().nextInt(0, 100));
            userList.add(user);
        }
        model.put("userInfo", userList);
        model.put("lineSeparator", System.getProperty("line.separator"));
        // 3.获取模板
        Template template = freemarkerConfiguration.getTemplate("userInfo.txt.ftl");
        // 4.使用模板生成文本、输出
        OutputStreamWriter out = new OutputStreamWriter(response.getOutputStream());
        template.process(model, out);
    }
}
```







***<u>END.</u>***