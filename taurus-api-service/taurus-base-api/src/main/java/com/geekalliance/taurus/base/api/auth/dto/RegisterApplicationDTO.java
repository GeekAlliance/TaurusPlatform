package com.geekalliance.taurus.base.api.auth.dto;

import com.geekalliance.taurus.base.api.auth.entity.Application;
import com.geekalliance.taurus.base.api.auth.entity.Resource;
import lombok.Data;

import java.util.List;

@Data
public class RegisterApplicationDTO {
    private List<Application> applications;

    private List<Resource> resources;

    private String userId;
}
