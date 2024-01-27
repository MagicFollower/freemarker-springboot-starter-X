package org.example.controller;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.entity.User;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

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
