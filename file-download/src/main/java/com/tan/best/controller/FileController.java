package com.tan.best.controller;

import cn.hutool.core.io.FastByteArrayOutputStream;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.util.StrUtil;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;

@RestController
@RequestMapping(value = "file")
public class FileController {


    @GetMapping(value = "downLoad")
    @CrossOrigin(originPatterns = "http://localhost:8081")
    public void downLoad(ServletResponse response) throws IOException {
        FileReader fileReader = new FileReader("./static/document/test20221215.txt");
        BufferedInputStream inputStream = fileReader.getInputStream();
        ServletOutputStream outputStream = response.getOutputStream();
        FastByteArrayOutputStream s = IoUtil.read(inputStream,false);
        while(StrUtil.isNotEmpty(s.toString())){
            IoUtil.writeUtf8(outputStream, false, s);
            s = IoUtil.read(inputStream,false);
        }
        IoUtil.close(()->{
            if(inputStream != null){
                inputStream.close();
            }
            if(outputStream!=null){
                outputStream.close();
            }
        });
    }
}
