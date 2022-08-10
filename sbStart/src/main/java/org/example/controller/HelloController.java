package org.example.controller;

import com.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.form.HelloForm;
import org.example.pojo.PeopleForm;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hello")
@Slf4j
public class HelloController {

    @PostMapping("post")
    public String hello(@RequestBody  HelloForm helloForm){
        String form = JsonUtil.toJsonString(helloForm);
        log.info(form);
        return "hello "+ helloForm.getAge();
    }

    @PostMapping("/hello")
    public String hello(@RequestBody PeopleForm peopleForm){

        return "hello " + peopleForm.getName();
    }
}
