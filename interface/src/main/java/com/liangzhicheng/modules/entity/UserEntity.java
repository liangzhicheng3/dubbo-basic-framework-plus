package com.liangzhicheng.modules.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.liangzhicheng.config.mvc.validate.annotation.FlagValidate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("user")
public class UserEntity extends Model<UserEntity> {

    /**
     * 用户id
     */
    private Long id;

    /**
     * 用户名称
     */
    @NotBlank(message = "用户名称不能为空")
    private String username;

    @FlagValidate(value = {"1", "2"}, message = "状态不正确")
    @TableField(exist = false)
    private Integer status;

}
