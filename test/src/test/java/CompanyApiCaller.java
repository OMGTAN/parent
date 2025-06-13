import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CompanyApiCaller {

    private static final String FILE_PATH = "C:\\Users\\tan\\Desktop\\1\\上海药坦药物研究开发有限公司.txt";
    private static final String API_URL = "http://10.21.15.163:6005/service/suaeeDc."; // 替换成实际接口地址
    private static final String API_URL2 = "@etm.suaee/service"; // 替换成实际接口地址

    public static void main(String[] args) {
        String [] inf = new String[]{"f3010004","f3010005","f3010006","f3010007","f3010008","f3010009","f3010010","f3010011","f3010012","f3010013","f3010014","f3010015","f3010016","f3010022","f3010023","f3010024","f3010025","f3010026"};
        for (int i = 0; i < inf.length; i++) {

            start(inf[i]);
        }
    }

    private static void start(String id) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String companyName;
            while ((companyName = br.readLine()) != null) {
                if (companyName.trim().isEmpty()) continue;

                System.out.println("正在处理公司：" + companyName);

                // 构建请求体
                Map<String, Object> request = buildRequestBody(companyName, id);

                // 发送请求
                sendPostRequest(request, id);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Map<String, Object> buildRequestBody(String companyName, String id) {
        Map<String, Object> requestBody = new HashMap<>();

        Map<String, String> head = new HashMap<>();
        head.put("x-ams-token", "2445f555f28f1ff1395e997e00eba49d");

        Map<String, Object> data = new HashMap<>();
        data.put("sessionId", "e65424c41c19475e88f76f77a2428b35");
        data.put("serviceId", "suaeeDc."+ id);

        String bodyJson = "{\"corp_name\":\"" + companyName + "\"}";
        data.put("body", bodyJson);

        requestBody.put("head", head);
        requestBody.put("data", data);
        requestBody.put("namespace", "suaee");
        requestBody.put("sessionId", "e65424c41c19475e88f76f77a2428b35");

        return requestBody;
    }

    private static void sendPostRequest(Map<String, Object> requestBody, String id) {

        String body = HttpRequest.post(API_URL+id + API_URL2).body(JSONUtil.toJsonStr(requestBody)).execute().body();
        System.out.println(body);
    }
}