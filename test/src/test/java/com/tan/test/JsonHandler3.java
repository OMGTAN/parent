package com.tan.test;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.tan.test.model.Person;
import com.tan.test.model.Person2;

import java.util.ArrayList;
import java.util.List;

public class JsonHandler3 {

    public static void main(String[] args) {
//
        Person p1 = new Person();
        List<Person> list = new ArrayList<>();
        list.add(p1);
        String jsonString1 = JSON.toJSONString(list);

        JSONConfig jsonConfig = JSONConfig.create();
        jsonConfig.setIgnoreCase(true);

        JSONArray person2s = JSONUtil.parseArray(jsonString1, jsonConfig);
        List<Person2> list1 = person2s.toList(Person2.class);
        System.out.println(list1.get(0).getName());

    }
}
