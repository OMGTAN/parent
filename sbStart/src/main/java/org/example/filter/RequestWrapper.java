package org.example.filter;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.example.util.JsonUtil;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.util.Map;

@Slf4j
public class RequestWrapper extends HttpServletRequestWrapper {
    //参数字节数组
    private byte[] requestBody;
    //Http请求对象
    private HttpServletRequest request; 
    public RequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        this.request = request;
    }
 
    /**
     * @return
     * @throws IOException
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {
        /**
         * 每次调用此方法时将数据流中的数据读取出来，然后再回填到InputStream之中
         * 解决通过@RequestBody和@RequestParam（POST方式）读取一次后控制器拿不到参数问题
         */
        if (null == this.requestBody) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            IOUtils.copy(request.getInputStream(), baos);
            this.requestBody = baos.toByteArray();
            JsonNode jsonNode = JsonUtil.getInstance().readTree(requestBody);
            JsonNode data = jsonNode.get("data");
            String s = data.toString();
            log.info("data: {}", s);
            Object stringObjectMap = null;
            try {
                stringObjectMap = JsonUtil.json2pojo(s, Object.class);
                //读取requestbody后再放入获取到的object
                this.requestBody = stringObjectMap.toString().getBytes();
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
 
        final ByteArrayInputStream bais = new ByteArrayInputStream(requestBody);
        return new ServletInputStream() {
 
            @Override
            public boolean isFinished() {
                return false;
            }
 
            @Override
            public boolean isReady() {
                return false;
            }
 
            @Override
            public void setReadListener(ReadListener listener) {
 
            }
 
            @Override
            public int read() {
                return bais.read();
            }
        };
    }
 
    public byte[] getRequestBody() {
        return requestBody;
    }
 
    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }
}