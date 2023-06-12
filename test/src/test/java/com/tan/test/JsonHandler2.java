package com.tan.test;


import com.tan.test.model.Person;
import com.tan.test.model.Person2;

public class JsonHandler2 {

    public static void main(String[] args) {
//
        Person p1 = new Person();
        p1.setName("name1");
        p1.setAge(11);
//        String jsonString = JsonUtils.serialize(p1);
//        System.out.println(jsonString);
//
//
//        Person2 p2 = JsonUtils.parse(jsonString, Person2.class);
//        System.out.println(p2.getNAme());
//        System.out.println(p2.getAGe());
//
//        jsonString = JsonUtils.serialize(p2);
//        System.out.println(jsonString);
//
//        List<Person> list = new ArrayList<>();
//        list.add(p1);
//        String jsonString1 = JsonUtils.serialize(list);
//        List<Person2> person2s = JsonUtils.parseList(jsonString1, Person2.class);
//        System.out.println("person2s.get(0).getNAme(): "+person2s.get(0).getNAme());



        Person2 person2 = new Person2();
        person2.setAGe(21);
        person2.setName("p22");
        person2.setPpp(p1);
        String serialize = JsonUtils.serialize(person2);
        System.out.println(serialize);


        Person2 parse = JsonUtils.parse("{\"pPp\":{\"naMe\":\"naMe1\",\"age\":11},\"naMe\":\"p22\",\"age\":21}", Person2.class);
        System.out.println("person2s.get(0).getNAme(): "+parse.getPpp().getName());

    }
}
