package org.example.service.impl;

import com.common.model.ResposeModel;
import com.common.util.FileUtil;
import com.common.util.JsonUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.example.dao.vo.FileVO;
import org.example.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.net.URLEncoder;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@Slf4j
public class FileServiceImpl  implements FileService {

    @Value("${file.path}")
    private String FILE_PATH;

    private static String ZIP_FILE_NAME="total.zip";

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

    public ResposeModel fileDownLoad(HttpServletResponse response, String filePath,  String fileName){
        filePath = StringUtils.isEmpty(filePath)?FILE_PATH:filePath;
        File file = new File( filePath+'/'+ fileName);
        if(!file.exists()){
            return ResposeModel.failed("下载文件不存在");
        }
        response.reset();
//        response.setContentType("application/x-download");
        response.setContentLength((int) file.length());
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            log.error("文件名加密错误！");
            throw new RuntimeException("文件名加密错误");
        }

        try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));) {
            byte[] buff = new byte[1024];
            OutputStream os  = response.getOutputStream();
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
        } catch (IOException e) {
            log.error("",e);
            return ResposeModel.failed("下载失败");
        }
        return ResposeModel.success("下载成功");
    }

    public ResposeModel pictureDownLoad(HttpServletResponse response, String filePath,  String fileName){
        filePath = StringUtils.isEmpty(filePath)?FILE_PATH:filePath;
        File file = new File( filePath+'/'+ fileName);
        if(!file.exists()){
            return ResposeModel.failed("下载图片不存在");
        }
        byte[] bytes = null;
        try {
            bytes = FileUtil.inputStream2Bytes(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return ResposeModel.success(DatatypeConverter.printBase64Binary(bytes));
    }

    @Override
    public ResposeModel uploadPic(byte[] fileBytes) {
        File file = new File(FILE_PATH+File.separatorChar+"111.jpg");
        try (FileOutputStream fos = new FileOutputStream(file);
                BufferedOutputStream bos = new BufferedOutputStream(fos)){

            bos.write(fileBytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResposeModel.success("上传成功！");
    }

    @SneakyThrows
    @Override
    public ResposeModel byteDownLoad(HttpServletResponse response, String filePath, String fileName) {
        filePath = StringUtils.isEmpty(filePath)?FILE_PATH:filePath;
        File file = new File( filePath+'/'+ fileName);
        if(!file.exists()){
            return ResposeModel.failed("下载文件不存在");
        }
        byte[] bytes = FileUtil.inputStream2Bytes(new FileInputStream(file));
        return ResposeModel.success(bytes);
    }

    @Override
    public void downloadByteArray(HttpServletResponse response) throws IOException {
        File file = new File(FILE_PATH);
        File[] files = file.listFiles();
        Map<String, String> result = new HashMap<>();
        for (File f : files) {
            if(f.isDirectory()){
                continue;
            }
            byte[] bytes = FileUtil.inputStream2Bytes(new FileInputStream(f));
            result.put(f.getName(), DatatypeConverter.printBase64Binary(bytes));
        }

        response.reset();
        response.setContentType("application/x-download");
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("bytes.json", "utf-8"));
        } catch (UnsupportedEncodingException e) {
            log.error("文件名加密错误！");
            throw new RuntimeException("文件名加密错误");
        }
        String s = JsonUtil.toJsonString(result);
        log.info("length:      {}",s.getBytes().length);
        try(BufferedInputStream bis = new BufferedInputStream(new ByteArrayInputStream(s.getBytes()))) {
            byte[] buff = new byte[1024];
            OutputStream os  = response.getOutputStream();
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
        } catch (IOException e) {
            log.error("",e);
        }

    }

    @Override
    public void uploadByteArray(HttpServletResponse response, MultipartFile file) {
        byte[] oet= null;
        try (InputStream is = file.getInputStream();
             ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            IOUtils.copy(is, os);
            byte[] bytes = os.toByteArray();
            String s = new String(bytes);
            Map<String, String> map = JsonUtil.toObject(s, Map.class);
            Set<Map.Entry<String, String>> set = map.entrySet();
            for (Map.Entry<String, String> entry : set) {
                String key = entry.getKey();
                if("OET.jpg".equals(key)){
                    String value = entry.getValue();
                    oet = DatatypeConverter.parseBase64Binary(value);

                }
            }
        } catch (IOException e) {
            log.error("文件上传发生异常 -> {}", e.getMessage());
        }
        response.reset();
        response.setContentType("application/x-download");
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("123.jpg", "utf-8"));
        } catch (UnsupportedEncodingException e) {
            log.error("文件名加密错误！");
            throw new RuntimeException("文件名加密错误");
        }
        try(BufferedInputStream bis = new BufferedInputStream(new ByteArrayInputStream(oet))) {
            byte[] buff = new byte[1024];
            OutputStream os  = response.getOutputStream();
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
        } catch (IOException e) {
            log.error("",e);
        }
    }


    @Override
    public ResposeModel zipDownLoad(HttpServletResponse response, String filePath) {
        String zipPath = FILE_PATH + java.io.File.separatorChar +"zip";
        zipPath=StringUtils.isEmpty(filePath)? zipPath:filePath;
        File fd = new File(zipPath+ java.io.File.separatorChar + ZIP_FILE_NAME);
        zipFiles(zipPath, null);
        this.fileDownLoad(response, zipPath, ZIP_FILE_NAME);
//        fd.deleteOnExit();
        return ResposeModel.failed("下载失败！");
    }

    void zipFiles(String zipPath, String zipName){
        try(ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipPath+ java.io.File.separatorChar + ZIP_FILE_NAME))){
            byte[] buffer = new byte[1024];
            File file = new File(FILE_PATH);
            File[] files = file.listFiles();
            for (File f : files) {
                if(f.isDirectory()){
                    continue;
                }
                try (FileInputStream fis = new FileInputStream(f)){
                    out.putNextEntry(new ZipEntry(f.getName()));
                    int len =0;
                    while ((len = fis.read(buffer))>0){
                        out.write(buffer, 0, len);
                    }
                    out.closeEntry();
                }
            }
        } catch (FileNotFoundException e) {
            log.error("找不到文件：",e);
        } catch (IOException e) {
            log.error("io异常：",e);
        }
    }

}
