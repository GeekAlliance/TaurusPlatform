package com.geekalliance.taurus.base.oauth.controller;

import com.geekalliance.taurus.base.api.auth.entity.Resource;
import com.geekalliance.taurus.base.oauth.service.AuthResourceService;
import com.geekalliance.taurus.core.entity.BaseTreeNode;
import com.geekalliance.taurus.core.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author maxuqiang
 */
@Api(value = "权限资源", tags = {"权限资源"})
@Slf4j
@RestController
@RequestMapping("/permission")
public class AuthResourceController {
    @Autowired
    private AuthResourceService authResourceService;

    @ApiOperation(value = "查询用户授权的模块信息")
    @GetMapping(value = "/module")
    public Result<List<BaseTreeNode<Resource>>> getPermissionModule() {
        List<BaseTreeNode<Resource>> resourceTree = authResourceService.getPermissionModule();
        return Result.success(resourceTree);
    }

    @ApiOperation(value = "查询用户授权的行为信息")
    @GetMapping(value = "/action/{resourceId}")
    public Result<List<Resource>> getPermissionAction(@PathVariable("resourceId") String resourceId) {
        List<Resource> resources = authResourceService.getPermissionAction(resourceId);
        return Result.success(resources);
    }

    @ApiOperation(value = "根据权限标识验证是否有权限")
    @GetMapping(value = "/{permitFlag}")
    public Result<Boolean> hasPermission(@PathVariable("permitFlag") String permitFlag) {
        boolean result = authResourceService.hasPermissionByPermitFlag(permitFlag);
        return Result.success(result);
    }
}