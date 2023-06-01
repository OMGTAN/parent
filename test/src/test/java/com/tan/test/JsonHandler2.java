package com.tan.test;

import com.tan.test.model.Person;
import com.tan.test.model.Person2;

import java.util.ArrayList;
import java.util.List;

public class JsonHandler2 {

    public static void main(String[] args) {

        Person p1 = new Person();
        p1.setName("name1");
        p1.setAge(11);
        String jsonString = JsonUtils.serialize(p1);
        System.out.println(jsonString);


        Person2 p2 = JsonUtils.parse(jsonString, Person2.class);
        System.out.println(p2.getNAme());
        System.out.println(p2.getAGe());


//        p2.setNAme("nn1");
//        p2.setAGe(11);
        jsonString = JsonUtils.serialize(p2);
        System.out.println(jsonString);

        List<Person> list = new ArrayList<>();
        list.add(p1);
        String jsonString1 = JsonUtils.serialize(list);
        List<Person2> person2s = JsonUtils.parseList(jsonString1, Person2.class);
        System.out.println("person2s.get(0).getNAme()");
        System.out.println(person2s.get(0).getNAme());
    }
}
