package org.example.controller;

import com.common.model.ResposeModel;
import com.common.util.FileUtil;
import com.common.util.JsonUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.example.dao.vo.FileVO;
import org.example.service.FileService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.net.URLEncoder;

@Slf4j
@RestController
@Api(tags = "文件上传接口")
@RequestMapping("/file")
public class FileController {

    @Resource
    private FileService fileService;


    @ApiOperation("文件单个上传")
    @PostMapping("/uploadByOne")
    public ResposeModel uploadByOne(@ApiParam(value = "文件", name = "file") @RequestPart("file") MultipartFile file) {
        try (InputStream is = file.getInputStream();
             ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            IOUtils.copy(is, os);
            byte[] bytes = os.toByteArray();
            FileVO vo = fileService.storeFile(bytes, file.getOriginalFilename());
            return ResposeModel.success(vo);
        } catch (IOException e) {
            log.error("文件上传发生异常 -> {}", e.getMessage());
            return ResposeModel.failed("文件上传失败");
        }
    }

    @ApiOperation("根据文件名下载")
    @GetMapping(value = "/download")
    public ResposeModel fileDownLoad(HttpServletResponse response, @RequestParam(value = "filePath", required = false) String filePath, @RequestParam("fileName") String fileName){
          fileService.fileDownLoad(response, filePath, fileName);
          return ResposeModel.success("11");
    }



    @ApiOperation("打包下载")
    @GetMapping("/zipDownLoad")
    public void zipDownLoad(HttpServletResponse response, @RequestParam("filePath") String filePath){
         fileService.zipDownLoad(response, filePath);
    }


    @ApiOperation("根据名称下载图片")
    @GetMapping(value = "/download/picture")
    public ResposeModel pictureDownLoad(HttpServletResponse response, @RequestParam(value = "filePath", required = false) String filePath, @RequestParam("fileName") String fileName){
        return fileService.pictureDownLoad(response, filePath, fileName);
    }

    @ApiOperation("图片单个上传")
    @PostMapping("/uploadPic")
    public ResposeModel uploadPic(@ApiParam(value = "文件", name = "fileBytes") @RequestPart("fileBytes") String fileBytes) {

        return fileService.uploadPic(DatatypeConverter.parseBase64Binary(fileBytes));
    }


    @ApiOperation("根据名称下载byte数组")
    @GetMapping(value = "/download/byte")
    public ResposeModel byteDownLoad(HttpServletResponse response, @RequestParam(value = "filePath", required = false) String filePath, @RequestParam("fileName") String fileName){
        ResposeModel resposeModel = fileService.byteDownLoad(response, filePath, fileName);
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("你好.docx", "utf-8"));
        } catch (UnsupportedEncodingException e) {
            log.error("文件名加密错误！");
            throw new RuntimeException("文件名加密错误");
        }
        try(ServletOutputStream outputStream = response.getOutputStream()){
            outputStream.write((byte[])resposeModel.getData());
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @ApiOperation("下载byte[]文件")
    @GetMapping(value = "/download/byteArray")
    public ResposeModel downloadByteArray(HttpServletResponse response, @RequestParam(value = "filePath", required = false) String filePath, @RequestParam("fileName") String fileName){
        try {
            fileService.downloadByteArray(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @ApiOperation("解析byte[]文件")
    @PostMapping(value = "/upload/byteArray")
    public ResposeModel uploadByteArray(HttpServletResponse response, @ApiParam(value = "文件", name = "file") @RequestPart("file") MultipartFile file){
        fileService.uploadByteArray(response, file);
        return null;
    }
}
