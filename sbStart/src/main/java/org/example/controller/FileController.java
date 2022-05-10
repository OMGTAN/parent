package org.example.controller;

import com.common.model.ResposeModel;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.example.dao.vo.FileVO;
import org.example.service.FileService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

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

}
