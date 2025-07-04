import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class CompanyApiCaller {

    private static final String FILE_PATH = "C:\\Users\\tan\\Desktop\\1\\corpname.txt";
    private static final String API_URL = "http://10.21.15.163:6005/service/suaeeDc."; // 替换成实际接口地址
    private static final String API_URL2 = "@etm.suaee/service"; // 替换成实际接口地址

    public static void main(String[] args) {
        String [] inf = new String[]{
                "f3010030",};
        for (int i = 0; i < inf.length; i++) {
            System.out.println("正在处理接口：" + inf[i]);
            int start = start(inf[i]);
            System.out.println(inf[i]+"处理完成，共处理了" + start + "条数据");
        }
    }

    private static int start(String id) {
        int i = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String companyName;
            while ((companyName = br.readLine()) != null) {
                if (companyName.trim().isEmpty()) continue;

                System.out.println("正在处理第 " + i + " 条数据");
//                System.out.println("正在处理公司：" + companyName);

                // 构建请求体
                Map<String, Object> request = buildRequestBody(companyName, id);

                // 发送请求
                sendPostRequest(request, id);
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return i;
    }

    private static Map<String, Object> buildRequestBody(String companyName, String id) {
        Map<String, Object> requestBody = new HashMap<>();

        Map<String, String> head = new HashMap<>();
        head.put("x-ams-token", "2445f555f28f1ff1395e997e00eba49d");

        Map<String, Object> data = new HashMap<>();
        data.put("sessionId", "af26c63de56e4a8ab8f37fcb91cc710b");
        data.put("serviceId", "suaeeDc."+ id);

        String bodyJson = "{\"corp_name\":\"" + companyName + "\"}";
        data.put("body", bodyJson);

        requestBody.put("head", head);
        requestBody.put("data", data);
        requestBody.put("namespace", "suaee");
        requestBody.put("sessionId", "af26c63de56e4a8ab8f37fcb91cc710b");

        return requestBody;
    }

    private static void sendPostRequest(Map<String, Object> requestBody, String id) {

        String body = HttpRequest.post(API_URL+id + API_URL2).body(JSONUtil.toJsonStr(requestBody)).execute().body();
        System.out.println(body);
    }
}