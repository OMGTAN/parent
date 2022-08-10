package org.example.service;

import org.example.dao.form.FileForm;
import org.example.dao.vo.FileVO;

public interface FileService {


    /**
     * 文件上传
     * @param content
     * @param originFileName
     * @return
     */
    FileVO storeFile(byte[] content, String originFileName);

    /**
     * 存储文件到本地
     * @param content
     * @param path
     * @param fileName
     */
    void storeFileWithFileName(byte[] content, String path, String fileName);
}
