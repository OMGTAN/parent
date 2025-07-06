package com.tan.test.json;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.core.io.FileUtil;

import java.util.List;

/**
 * JSON解析工具类
 * 使用Hutool工具解析resp.json文件
 */
public class JsonParser {
    
    /**
     * 解析resp.json文件，将projectList字符串转换为JSONObject
     * @param filePath JSON文件路径
     * @return 解析后的JSONObject
     */
    public static JSONObject parseRespJson(String filePath) {
        try {
            // 读取JSON文件
            String jsonContent = FileUtil.readUtf8String(filePath);
            
            // 解析JSON
            JSONObject jsonObject = JSONUtil.parseObj(jsonContent);
            
            // 获取records数组
            JSONArray records = jsonObject.getJSONArray("records");
            
            if (records != null && !records.isEmpty()) {
                // 遍历records数组
                for (int i = 0; i < records.size(); i++) {
                    JSONObject record = records.getJSONObject(i);
                    
                    // 获取projectList字符串
                    String projectListStr = record.getStr("projectList");
                    
                    if (projectListStr != null && !projectListStr.isEmpty()) {
                        // 将字符串解析为JSONArray
                        JSONArray projectArray = JSONUtil.parseArray(projectListStr);
                        
                        // 将JSONArray设置回record中，替换原来的字符串
                        record.set("projectList", projectArray);
                    }
                }
            }
            
            return jsonObject;
            
        } catch (Exception e) {
            System.err.println("解析JSON文件失败: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 获取所有项目信息
     * @param filePath JSON文件路径
     * @return 项目列表
     */
    public static List<JSONObject> getAllProjects(String filePath) {
        JSONObject parsedJson = parseRespJson(filePath);
        if (parsedJson == null) {
            return null;
        }
        
        JSONArray records = parsedJson.getJSONArray("records");
        if (records == null || records.isEmpty()) {
            return null;
        }
        
        JSONObject firstRecord = records.getJSONObject(0);
        JSONArray projectList = firstRecord.getJSONArray("projectList");
        
        if (projectList == null) {
            return null;
        }
        
        return projectList.toList(JSONObject.class);
    }
    
    /**
     * 打印解析后的项目信息
     * @param filePath JSON文件路径
     */
    public static void printProjectInfo(String filePath) {
        List<JSONObject> projects = getAllProjects(filePath);
        
        if (projects == null) {
            System.out.println("没有找到项目信息");
            return;
        }
        
        System.out.println("项目信息:");
        System.out.println("总项目数: " + projects.size());
        System.out.println("========================");
        
        for (int i = 0; i < projects.size(); i++) {
            JSONObject project = projects.get(i);
            System.out.println("项目 " + (i + 1) + ":");
            System.out.println("  项目编号: " + project.getStr("projectNo"));
            System.out.println("  项目名称: " + project.getStr("projectName"));
            System.out.println("  发布类型: " + project.getStr("publishType"));
            System.out.println("  发布价格: " + project.getStr("publishPrice"));
            System.out.println("  资产类型: " + project.getStr("assetTypeDesc"));
            System.out.println("  发布开始日期: " + project.getStr("publishStartDate"));
            System.out.println("  发布结束日期: " + project.getStr("publishEndDate"));
            System.out.println("  发布URL: " + project.getStr("publishURL"));
            
            // 打印图片列表
            JSONArray imageList = project.getJSONArray("projectImageList");
            if (imageList != null && !imageList.isEmpty()) {
                System.out.println("  图片列表:");
                for (int j = 0; j < imageList.size(); j++) {
                    JSONObject image = imageList.getJSONObject(j);
                    System.out.println("    图片" + (j + 1) + ": " + image.getStr("filePath"));
                }
            }
            System.out.println("------------------------");
        }
    }
    
    /**
     * 主方法，用于测试
     */
    public static void main(String[] args) {
        String filePath = "D:\\repos\\gitee\\llm-study\\daas/resp.json";
        
        // 解析JSON
        JSONObject parsedJson = parseRespJson(filePath);
        String string = parsedJson.toString();
        if (parsedJson != null) {
            System.out.println("JSON解析成功!");
            System.out.println("返回码: " + parsedJson.getStr("retu_code"));
            System.out.println("备注: " + parsedJson.getStr("memo"));
        }
        
        // 打印项目信息
        printProjectInfo(filePath);
    }
} 