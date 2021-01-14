package com.geekalliance.taurus.base.api.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.geekalliance.taurus.core.entity.BaseEntity;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

    
/**
 * 消息表
 * @author geekeeper
 * @version V1.0.0.0
 * @date 2021-01-14 20:55
 *
 */
@Data
@EqualsAndHashCode(callSuper=true)
@TableName("base_message")
public class Message extends BaseEntity {
    /**
    * 标题
    */
    private String title;

    /**
    * 内容
    */
    private String content;

    /**
    * 消息类型
    */
    private Integer type;

    /**
    * 发布人
    */
    private String publisher;

    /**
    * 发布时间
    */
    private Date releaseTime;

    /**
    * 撤回时间
    */
    private Date cancelTime;

    /**
    * 状态
    */
    private Integer statusNumber;

    /**
    * 删除标识
    */
    private String deleteFlag;
}