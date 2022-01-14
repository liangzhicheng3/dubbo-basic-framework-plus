package com.liangzhicheng.modules.impl;

import com.github.pagehelper.PageInfo;
import com.liangzhicheng.common.response.WrapperHelper;
import com.liangzhicheng.common.utils.BeansUtil;
import com.liangzhicheng.common.utils.ListUtil;
import com.liangzhicheng.modules.entity.UserEntity;
import com.liangzhicheng.modules.entity.dto.UserDTO;
import com.liangzhicheng.modules.entity.query.UserQueryCondition;
import com.liangzhicheng.modules.entity.vo.UserVO;
import com.liangzhicheng.modules.mapper.IUserMapper;
import com.liangzhicheng.modules.service.IUserService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 3.0.x版本后，原先的dubbo @Service和spring @Service做区分，修改为@DubboService，
 * 表示将接口暴露在外提供调用，且注册到注册中心中
 * 相应的@Reference，修改为@DubboReference
 */
@DubboService
@Service
public class UserServiceImpl extends BaseServiceImpl<IUserMapper, UserEntity> implements IUserService {

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public UserEntity getById(Long userId) {
        return baseMapper.selectById(userId);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Map<String, Object> testUserList(UserDTO userDTO, Pageable pageable) {
        UserQueryCondition userQuery = new UserQueryCondition(userDTO);
        //自定义排序
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.DESC,"id"));
        super.pageHandle(super.customizeSort(userQuery, orders), userQuery.getPageNum(), userQuery.getPageSize());
        //只根据创建时间排序
//        super.pageHandle(pageable, userQuery.getPageNum(), userQuery.getPageSize());
        List<UserEntity> personList = baseMapper.selectList(
                WrapperHelper.getInstance().buildCondition(UserEntity.class, userQuery));
        PageInfo pageInfo = new PageInfo<>();
        List<?> records = null;
        if(ListUtil.sizeGT(personList)){
            pageInfo = new PageInfo<>(personList);
            records = BeansUtil.copyList(pageInfo.getList(), UserVO.class);
        }
        return super.pageResult(records, pageInfo);
    }

}
