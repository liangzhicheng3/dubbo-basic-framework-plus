package com.liangzhicheng.modules.controller;

import com.liangzhicheng.common.basic.BaseController;
import com.liangzhicheng.common.constant.ApiConstant;
import com.liangzhicheng.common.response.ResponseResult;
import com.liangzhicheng.common.utils.AESUtil;
import com.liangzhicheng.common.utils.ListUtil;
import com.liangzhicheng.config.aop.annotation.LogRecord;
import com.liangzhicheng.modules.entity.UserEntity;
import com.liangzhicheng.modules.entity.dto.UserDTO;
import com.liangzhicheng.modules.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

@Api(value="TestController", tags={"测试控制器"})
@RestController
@RequestMapping(value = "/test")
public class TestController extends BaseController {

    @DubboReference
    private IUserService userService;

    @LogRecord(operate = "测试Validated")
    @ApiOperation(value = "测试Validated")
    @PostMapping(value = "/testValidated")
    public ResponseResult testValidated(@Validated @RequestBody UserEntity user,
                                        BindingResult bindingResult){
        if("易成".equals(user.getUsername())){
            return buildSuccessInfo();
        }
        return buildFailedInfo(ApiConstant.BASE_FAIL_CODE);
    }

    @ApiOperation(value = "测试InitBinder")
    @GetMapping(value = "/testInitBinder")
    public ResponseResult testInitBinder(String str){
        Map<String, Object> map = new HashMap<>();
        map.put("str", str);
        return buildSuccessInfo(map.toString());
    }

    @ApiOperation(value = "测试AES")
    @PostMapping(value = "/testAES")
    public ResponseResult testAES(){
        //加密传值
        String value = "userId=123&orderId=456";
        String sign = AESUtil.aesEncrypt(value);
        //接收解密
        String userId = AESUtil.getParam("userId", sign);
        String orderId = AESUtil.getParam("orderId", sign);
        Map<String, Object> map = new HashMap<>(2);
        map.put("userId", userId);
        map.put("orderId", orderId);
        return buildSuccessInfo(map);
    }

    @ApiOperation(value = "测试资源json文件获取")
    @PostMapping(value = "/testGetJson")
    public ResponseResult testGetJson(){
        List<UserEntity> list = ListUtil.toList("json/user.json", UserEntity.class);
        return buildSuccessInfo(list);
    }

    @ApiOperation(value = "测试自定义排序查询列表")
    @PostMapping(value = "/testUserList")
    public ResponseResult testUserList(@RequestBody UserDTO userDTO,
                                       Pageable pageable){
        return buildSuccessInfo(userService.testUserList(userDTO, pageable));
    }

}
