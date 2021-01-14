package com.geekalliance.taurus.base.auth.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.geekalliance.taurus.base.api.auth.dto.BaseUserPageDTO;
import com.geekalliance.taurus.base.api.auth.dto.QueryBaseUserDTO;
import com.geekalliance.taurus.base.api.auth.dto.SaveBaseUserDTO;
import com.geekalliance.taurus.base.api.auth.entity.BaseUser;
import com.geekalliance.taurus.base.auth.service.BaseUserService;
import com.geekalliance.taurus.base.auth.service.ResourceService;
import com.geekalliance.taurus.core.exception.ApiException;
import com.geekalliance.taurus.core.params.PageQueryParam;
import com.geekalliance.taurus.core.result.PageResult;
import com.geekalliance.taurus.core.result.Result;
import com.geekalliance.taurus.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(value = "资源管理", tags = {"资源管理"})
@Slf4j
@RestController
@RequestMapping("/v1/resource")
public class ResourceController extends BaseController {
    @Autowired
    private ResourceService resourceService;

}
