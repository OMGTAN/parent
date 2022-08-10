package com.tan.controller;

import com.common.util.BeanUtil;
import com.common.util.JsonUtil;
import com.tan.dao.form.HelloForm;
import com.tan.feign.UserClient;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("test")
@Slf4j
public class TestController {

    @Autowired
    UserClient userClient;

    @PostMapping("post")

    public String hello(HelloForm helloForm){
        helloForm.setName("www");
        helloForm.setAge(10);
        String form = JsonUtil.toJsonString(helloForm);
        log.info(form);
        Map map = JsonUtil.toObject(form, Map.class);
        Map<String, Object> stringObjectMap = BeanUtil.beanToMap(helloForm);
        String s = userClient.basePost("/hello/post", form);

        return s;
    }
}
