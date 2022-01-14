package com.liangzhicheng.modules.controller;

import com.liangzhicheng.common.basic.BaseController;
import com.liangzhicheng.common.response.ResponseResult;
import com.liangzhicheng.modules.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value="UserController", tags={"用户控制器"})
@RestController
@RequestMapping(value = "/user")
public class UserController extends BaseController {

    @DubboReference
    private IUserService userService;

    @ApiOperation(value = "获取用户信息")
    @GetMapping(value = "/{userId}")
    public ResponseResult getById(@PathVariable("userId") Long userId){
        return buildSuccessInfo(userService.getById(userId));
    }

}
