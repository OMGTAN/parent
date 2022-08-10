package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.pojo.PeopleForm;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HelloController {

    @PostMapping("/hello")
    public String hello(@RequestBody PeopleForm peopleForm){

        return "hello " + peopleForm.getName();
    }
}
