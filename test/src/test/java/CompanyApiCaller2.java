import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompanyApiCaller2 {

    private static final String FILE_PATH = "C:\\Users\\tan\\Desktop\\1\\corpname.txt";
    private static final String API_URL = "http://10.21.15.163:6005/service/suaeeDc."; // 替换成实际接口地址
    private static final String API_URL2 = "@etm.suaee/service"; // 替换成实际接口地址

    public static void main(String[] args) {
        String [] inf = new String[]{
                "f3010024",
                "f3010007",
                "f3010025",
                "f3010023",
                "f3010026",
                "f3010028",
                "f3010027",
                "f3010029",
                "f3010030",
                "f3010035",
                "f3010036",
                "f3010037",
                "f3030001",};
        for (int i = 0; i < inf.length; i++) {
            System.out.println("正在处理接口：" + inf[i] + " " + LocalDateTime.now());
            int start = start(inf[i]);
            System.out.println(inf[i]+"处理完成，共处理了" + start + "条数据");
        }
    }

    private static int start(String id) {
        int i = 0;
        int threadCount = 21; // 可根据实际情况调整线程数
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String companyName;
            // 先统计总行数
            int total = 0;
            br.mark(10 * 1024 * 1024); // 10MB mark
            while (br.readLine() != null) total++;
            br.reset();
            CountDownLatch latch = new CountDownLatch(total);
            while ((companyName = br.readLine()) != null) {
                final String name = companyName;
                if (name.trim().isEmpty()) {
                    latch.countDown();
                    continue;
                }
                final int idx = i;
                executor.submit(() -> {
                    try {
                        System.out.println("正在处理第 " + idx + " 条数据");
                        Map<String, Object> request = buildRequestBody(name, id);
                        sendPostRequest(request, id, idx);
//                        Thread.currentThread().sleep(1000);
                    } catch (Exception e) {
	                    throw new RuntimeException(e);
                    } finally {
                        latch.countDown();
                    }
                });
                i++;
            }
            latch.await();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
        return i;
    }

    private static Map<String, Object> buildRequestBody(String companyName, String id) {
        Map<String, Object> requestBody = new HashMap<>();

        Map<String, String> head = new HashMap<>();
        head.put("x-ams-token", "2445f555f28f1ff1395e997e00eba49d");

        Map<String, Object> data = new HashMap<>();
        data.put("sessionId", "29015d7a702e45c5888f27d8b7ae92ea");
        data.put("serviceId", "suaeeDc."+ id);

        String bodyJson = "{\"corp_name\":\"" + companyName + "\"}";
        data.put("body", bodyJson);

        requestBody.put("head", head);
        requestBody.put("data", data);
        requestBody.put("namespace", "suaee");
        requestBody.put("sessionId", "29015d7a702e45c5888f27d8b7ae92ea");

        return requestBody;
    }

    private static void sendPostRequest(Map<String, Object> requestBody, String id, int idx) {

        String body = HttpRequest.post(API_URL+id + API_URL2).body(JSONUtil.toJsonStr(requestBody)).execute().body();
        System.out.println(idx +"  " +body);
    }
}