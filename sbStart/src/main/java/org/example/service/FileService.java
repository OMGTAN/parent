package org.example.service;

import com.common.model.ResposeModel;
import org.example.dao.vo.FileVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

    /**
     * 文件下载
     * @param response
     * @param fileName
     * @return
     */
    ResposeModel fileDownLoad(HttpServletResponse response, String filePath, String fileName);

    /**
     * 打包下载
     * @param response
     * @param filePath
     * @return
     */
    ResposeModel zipDownLoad(HttpServletResponse response, String filePath);

    ResposeModel pictureDownLoad(HttpServletResponse response, String filePath, String fileName);

    ResposeModel uploadPic(byte[] fileBytes);

    ResposeModel byteDownLoad(HttpServletResponse response, String filePath, String fileName);

    void downloadByteArray(HttpServletResponse response) throws IOException;

    void uploadByteArray(HttpServletResponse response, MultipartFile file);
}
