package com.geekalliance.taurus.base.api.auth.register.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
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
@XmlRootElement(name = "applications")
public class RegisterApplications implements Serializable {
    @XmlElement(name = "application")
    List<RegisterApplication> applications;
}
