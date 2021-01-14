package com.geekalliance.taurus.base.api.auth.register.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geekalliance.taurus.toolkit.utils.Md5Utils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.validation.Valid;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

/**
 * @Description
 * @Date 2019/12/22
 * @Author maxuqiang
 **/
@Data
@ApiModel
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "application")
public class RegisterApplication implements Serializable {
    @JsonIgnore
    private String id;

    @JsonIgnore
    @XmlAttribute(name = "code")
    private String code;

    @JsonIgnore
    @XmlAttribute(name = "moduleStartSortIndex")
    private Integer moduleStartSortIndex;

    @ApiModelProperty(value = "应用名称", example = "应用名称")
    @XmlAttribute(name = "name")
    private String name;

    @Valid
    @ApiModelProperty(value = "应用模块")
    @XmlElement(name = "module")
    private List<RegisterModule> modules;

    public String getId() {
        if (StringUtils.isNotBlank(this.id)) {
            return this.id;
        } else {
            return Md5Utils.encodeByMD5(this.code);
        }
    }
}
