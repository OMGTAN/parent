package com.tan.best.controller;

import com.tan.best.service.NoStaticResourceHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("video")
public class VideoController {

    @Autowired
    private NoStaticResourceHandler noStaticResourceHandler;

    @GetMapping("play")
    public void video(@RequestParam("fileName") String fileName, HttpServletRequest request, HttpServletResponse response){
        System.out.println("=======================fileName::  "+fileName);
        String path = "D:\\tools\\"+fileName+".mp4";
        File f = new File(path);
        request.setAttribute(noStaticResourceHandler.FILE_NAME, path);
        try {
            System.out.println("======================="+path);
            noStaticResourceHandler.handleRequest(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
