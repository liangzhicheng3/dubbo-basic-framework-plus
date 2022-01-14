package com.liangzhicheng.modules.entity.dto;

import com.liangzhicheng.modules.entity.dto.basic.BaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class UserDTO extends BaseDTO {

    @ApiModelProperty("用户id/用户名称")
    private String keyword;

}
