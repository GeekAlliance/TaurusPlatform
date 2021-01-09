package com.geekalliance.taurus.core.utils;


import com.geekalliance.taurus.core.entity.BaseEntity;
import com.geekalliance.taurus.core.holder.UserContextHolder;
import com.geekalliance.taurus.core.holder.entity.TokenUser;

import java.util.Date;

/**
 * @Description
 * @Date 2019/12/24
 * @Author maxuqiang
 **/

public class BaseEntityFillUtils {
    public static void setBaseEntity(BaseEntity entity, boolean isSave) {
        TokenUser user = UserContextHolder.getInstance().getTokenUser();
        entity.setUpdateBy(user.getId());
        entity.setUpdateAt(new Date());
        if (isSave) {
            entity.setCreateBy(user.getId());
            entity.setCreateAt(new Date());
        }
    }
}
