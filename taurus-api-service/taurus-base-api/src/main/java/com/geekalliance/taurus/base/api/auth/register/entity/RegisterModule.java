package com.geekalliance.taurus.base.api.auth.register.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geekalliance.taurus.toolkit.utils.Md5Utils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

/**
 * @Description
 * @Date 2019/12/19
 * @Author maxuqiang
 **/
@Data
@ApiModel(description = "")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "module")
public class RegisterModule implements Serializable {
    @JsonIgnore
    private String id;

    @JsonIgnore
    private String application;

    @JsonIgnore
    private String parent;

    @JsonIgnore
    private String level;

    @JsonIgnore
    @XmlAttribute(name = "route")
    private String route;

    @JsonIgnore
    @XmlAttribute(name = "icon")
    private String icon;

    @ApiModelProperty(value = "模块名称最长32位", example = "模块名称最长32位", required = true)
    @Length(max = 32, message = "模块名称最长32位")
    @NotEmpty(message = "模块名称不能为空")
    @XmlAttribute(name = "name")
    private String name;

    @ApiModelProperty(value = "模块编码", example = "模块编码")
    @JsonIgnore
    @XmlAttribute(name = "code")
    private String code;

    @Valid
    @ApiModelProperty(value = "模块行为")
    @XmlElement(name = "action")
    List<RegisterAction> actions;

    @Valid
    @ApiModelProperty(value = "子模块")
    @XmlElement(name = "module")
    List<RegisterModule> subModule;

    public String getId() {
        return Md5Utils.encodeByMD5(this.application + this.code);
    }
}
