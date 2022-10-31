package com.tan.best.controller;

import com.tan.best.data.ApiResult;
import com.tan.best.data.form.UserForm;
import com.tan.best.data.vo.UserVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 书籍接口
 */
@RequestMapping("books")
@RestController
public class UserController {

    /**
     * 保存用户
     * @param userForm
     */
    @PostMapping(path = "save")
    public ApiResult<UserVO> saveUser(@RequestBody UserForm userForm){
        return null;
    }
}
