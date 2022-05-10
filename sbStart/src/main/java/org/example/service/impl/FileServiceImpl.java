package org.example.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.example.dao.form.FileForm;
import org.example.dao.vo.FileVO;
import org.example.service.FileService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class FileServiceImpl  implements FileService {

    @Value("${file.path}")
    private String FILE_PATH;

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");


    @Override
    public FileVO storeFile(byte[] content, String originFileName) {
        // 获取文件后缀 生成目录路径
        String filePath = FILE_PATH + java.io.File.separatorChar;
        // 保存文件并返回文件名
       this.storeFileWithFileName(content, filePath, originFileName);

        FileVO result = new FileVO();
        result.setName(originFileName);
        return result;
    }

    @Override
    public void storeFileWithFileName(byte[] content, String path, String fileName) {
        // 目录不存在则创建
        java.io.File file = new java.io.File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        try (FileOutputStream os = new FileOutputStream(path + fileName);
             ByteArrayInputStream is = new ByteArrayInputStream(content)) {
            IOUtils.copy(is, os);
        } catch (IOException e) {
            log.error("存储文件到本地时发生异常：{}", e);
        }
    }



}
