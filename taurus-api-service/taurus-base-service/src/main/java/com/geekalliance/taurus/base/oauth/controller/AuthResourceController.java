package com.geekalliance.taurus.base.oauth.controller;

import com.geekalliance.taurus.base.api.auth.entity.Resource;
import com.geekalliance.taurus.base.oauth.service.AuthResourceService;
import com.geekalliance.taurus.core.entity.BaseTreeNode;
import com.geekalliance.taurus.core.holder.UserContextHolder;
import com.geekalliance.taurus.core.holder.entity.TokenUser;
import com.geekalliance.taurus.core.result.Result;
import com.geekalliance.taurus.core.utils.JwtUtils;
import com.geekalliance.taurus.toolkit.StringPool;
import com.geekalliance.taurus.web.utils.TokenUserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author maxuqiang
 */
@Api(value = "权限资源", tags = {"权限资源"})
@Slf4j
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
}