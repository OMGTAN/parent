package com.tan.feign;

import com.tan.dao.form.HelloForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(value = "provider")
public interface UserClient {

    @GetMapping("getUserName")
    public String getUserName(@RequestParam("userId") String userId);

    @PostMapping(value = "{api}", headers = "application/json")
    public String basePost(@PathVariable("api") String api, @RequestBody Object helloForm);
}