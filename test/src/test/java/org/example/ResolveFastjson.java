package org.example;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public class ResolveFastjson {

    public static void main(String[] args) {

    }

    JSONObject resolve(JSONObject jsonObject){
        Map<String, Object> innerMap = jsonObject.getInnerMap();

        

        return jsonObject;
    }

}
