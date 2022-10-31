package com.tan.best.data.vo;

import com.tan.best.data.SimpleUser;
import io.github.yedaxia.apidocs.RapMock;
import lombok.Data;

import java.util.List;

/**
 * @author yeguozhong yedaxia.github.com
 */
@Data
public class UserVO extends SimpleUser {

    @RapMock(limit = "1-10")
    private SimpleUser[] friends; //好友


    private Boolean isFollow; //是否关注

    private List<UserVO> follower;

}