package com.geekalliance.taurus.base.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.geekalliance.taurus.base.api.auth.dto.QueryUserDTO;
import com.geekalliance.taurus.base.api.auth.dto.BaseUserPageDTO;
import com.geekalliance.taurus.base.service.BaseUserService;
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

@Api(value = "用户管理", tags = {"用户管理"})
@Slf4j
@RestController
@RequestMapping("/v1/user")
public class BaseUserController extends BaseController {
    @Autowired
    private BaseUserService baseUserService;

    @ApiOperation(value = "分页查询")
    @PostMapping("/page")
    public Result<PageResult<BaseUserPageDTO>> pageQuery(@RequestBody @Valid PageQueryParam<QueryUserDTO> param) throws ApiException {
        IPage<BaseUserPageDTO> pageResult = baseUserService.pageQuery(param);
        return Result.success(getResultByPage(pageResult, BaseUserPageDTO.class));
    }
}
