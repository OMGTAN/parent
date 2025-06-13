import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CompanyApiCaller {

    private static final String FILE_PATH = "C:\\Users\\tan\\Desktop\\1\\上海药坦药物研究开发有限公司.txt";
    private static final String API_URL = "http://10.21.15.163:6005/service/suaeeDc.f3010026@etm.suaee/service"; // 替换成实际接口地址

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String companyName;
            while ((companyName = br.readLine()) != null) {
                if (companyName.trim().isEmpty()) continue;

                System.out.println("正在处理公司：" + companyName);

                // 构建请求体
                Map<String, Object> request = buildRequestBody(companyName);

                // 发送请求
                sendPostRequest(request);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Map<String, Object> buildRequestBody(String companyName) {
        Map<String, Object> requestBody = new HashMap<>();

        Map<String, String> head = new HashMap<>();
        head.put("x-ams-token", "2445f555f28f1ff1395e997e00eba49d");

        Map<String, Object> data = new HashMap<>();
        data.put("sessionId", "688140bdaf7e49eeac06b65f3fc3fa33");
        data.put("serviceId", "suaeeDc.f3010026");

        String bodyJson = "{\"corp_name\":\"" + companyName + "\"}";
        data.put("body", bodyJson);

        requestBody.put("head", head);
        requestBody.put("data", data);
        requestBody.put("namespace", "suaee");
        requestBody.put("sessionId", "688140bdaf7e49eeac06b65f3fc3fa33");

        return requestBody;
    }

    private static void sendPostRequest(Map<String, Object> requestBody) {

        String body = HttpRequest.post(API_URL).body(JSONUtil.toJsonStr(requestBody)).execute().body();
        System.out.println(body);
    }
}