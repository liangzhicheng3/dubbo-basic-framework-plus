package com.liangzhicheng.modules.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liangzhicheng.modules.entity.SysLogRecordEntity;
import com.liangzhicheng.modules.mapper.ISysLogRecordMapper;
import com.liangzhicheng.modules.service.ISysLogRecordService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

@DubboService
@Service
public class SysLogRecordServiceImpl extends ServiceImpl<ISysLogRecordMapper, SysLogRecordEntity> implements ISysLogRecordService {

}
