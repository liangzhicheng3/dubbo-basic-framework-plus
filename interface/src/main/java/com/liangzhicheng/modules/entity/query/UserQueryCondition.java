package com.liangzhicheng.modules.entity.query;

import com.liangzhicheng.common.response.annotation.Query;
import com.liangzhicheng.modules.entity.dto.UserDTO;
import com.liangzhicheng.modules.entity.query.basic.BaseQueryCondition;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * 用户查询条件封装类
 * @author liangzhicheng
 */
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Data
public class UserQueryCondition extends BaseQueryCondition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Query(type = Query.Type.INNER_LIKE, blurry = "id,username")
    private String keyword;

    public UserQueryCondition(UserDTO userDTO){
        super(userDTO);
        String keyword = userDTO.getKeyword();
        if(!StringUtils.isEmpty(keyword)) {
            this.keyword = keyword;
        }
    }

}
