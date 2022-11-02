package com.tan.best.service;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class NoStaticResourceHandler extends ResourceHttpRequestHandler {

    public static String FILE_NAME = "NON-STATIC-FILE";

    @Override
    protected Resource getResource(HttpServletRequest request) throws IOException {
        String fillePath = request.getAttribute(FILE_NAME).toString();
        return new FileSystemResource(fillePath);
    }
}
