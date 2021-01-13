package com.geekalliance.taurus.base.organization.controller;

import com.geekalliance.taurus.base.api.organization.dto.OrganizationParam;
import com.geekalliance.taurus.base.api.organization.entity.Organization;
import com.geekalliance.taurus.base.organization.service.OrganizationService;
import com.geekalliance.taurus.core.annotion.OperateLog;
import com.geekalliance.taurus.core.entity.ValidatedGroup;
import com.geekalliance.taurus.core.enums.OperateTypeEnum;
import com.geekalliance.taurus.core.result.Result;
import com.geekalliance.taurus.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author geekeeper
 * @version V1.0.0.0
 * @date 2021-01-10 20:35
 */
@Api(value = "组织管理", tags = {"组织管理"})
@Slf4j
@RestController
@RequestMapping("/v1/organization")
public class OrganizationController extends BaseController {

    @Resource
    private OrganizationService organizationService;

    @ApiOperation("新增")
    @PostMapping
    @OperateLog(BusinessName = "组织机构-新增", operateType = OperateTypeEnum.ADD)
    public Result add(@RequestBody @Validated(ValidatedGroup.add.class) OrganizationParam saveParam) {

        return organizationService.add(saveParam) ? Result.success() : Result.fail();
    }

    @ApiOperation("删除")
    @DeleteMapping
    @OperateLog(BusinessName = "组织机构-删除", operateType = OperateTypeEnum.DELETE)
    public Result delete(@RequestBody @Validated(ValidatedGroup.delete.class) OrganizationParam deleteParam) {
        return organizationService.delete(deleteParam) ? Result.success() : Result.fail();
    }

    @ApiOperation("编辑")
    @PutMapping
    @OperateLog(BusinessName = "组织机构-编辑", operateType = OperateTypeEnum.UPDATE)
    public Result update(@RequestBody @Validated(ValidatedGroup.update.class) OrganizationParam updateParam) {
        return organizationService.update(updateParam) ? Result.success() : Result.fail();
    }

    @ApiOperation("组织树")
    @GetMapping("/tree")
    @OperateLog(BusinessName = "组织机构-树", operateType = OperateTypeEnum.TREE)
    public Result tree() {
        return Result.success(organizationService.tree());
    }
}
