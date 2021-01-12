package com.geekalliance.taurus.base.api.auth.register.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hollysys.platform.common.core.utils.Md5Utils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @Description
 * @Date 2019/12/19
 * @Author maxuqiang
 **/
@ApiModel
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "action")
public class RegisterAction implements Serializable {
    @JsonIgnore
    private String id;

    @JsonIgnore
    private String moduleId;

    @ApiModelProperty(value = "行为名称最长32位", example = "行为名称最长32位" ,required = true)
    @Length(max = 32, message = "行为名称最长32位")
    @NotEmpty(message = "行为名称不能为空")
    @XmlAttribute(name = "name")
    private String name;

    @ApiModelProperty(value = "行为编码最长32位", example = "行为编码最长32位" ,required = true)
    @Length(max = 32, message = "行为编码最长32位")
    @NotEmpty(message = "行为编码不能为空")
    @XmlAttribute(name = "code")
    private String code;

    public String getId() {
        return Md5Utils.encodeByMD5(this.moduleId+this.code);
    }
}
