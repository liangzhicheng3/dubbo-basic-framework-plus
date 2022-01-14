package com.liangzhicheng.modules.service;

import com.liangzhicheng.modules.entity.UserEntity;
import com.liangzhicheng.modules.entity.dto.UserDTO;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface IUserService extends IBaseService<UserEntity> {

    UserEntity getById(Long userId);

    Map<String, Object> testUserList(UserDTO userDTO, Pageable pageable);

}
