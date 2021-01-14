package com.geekalliance.taurus.base.api.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.geekalliance.taurus.core.entity.BaseEntity;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

    
/**
 *
 * 消息接收表
 * @author geekeeper
 * @version V1.0.0.0
 * @date 2021-01-14 20:55
 *
 */
@Data
@EqualsAndHashCode(callSuper=true)
@TableName("base_message_user")
public class MessageUser extends BaseEntity {
    /**
    * 消息编号
    */
    private String messageId;

    /**
    * 用户编号
    */
    private String userId;

    /**
    * 状态
    */
    private Integer statusNumber;

    /**
    * 阅读时间
    */
    private Date readTime;
}