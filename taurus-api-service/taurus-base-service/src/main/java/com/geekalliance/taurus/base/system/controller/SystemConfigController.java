package com.geekalliance.taurus.base.system.controller;

import com.geekalliance.taurus.base.api.system.dto.AddSystemConfigDTO;
import com.geekalliance.taurus.core.params.CommonDeleteParam;
import com.geekalliance.taurus.base.api.system.dto.QuerySystemConfigDTO;
import com.geekalliance.taurus.base.api.system.dto.UpdateConfigDTO;
import com.geekalliance.taurus.base.system.service.SystemConfigService;
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
import java.util.List;

/**
 * DictionaryController
 *
 * @author geekeeper
 * @email geekkeeper@163.com
 * @date 2021-01-14 22:08
 */
@Api(value = "配置管理", tags = {"配置管理"})
@Slf4j
@RestController
@RequestMapping("/v1/systemConfig")
public class SystemConfigController extends BaseController {

    @Resource
    private SystemConfigService systemConfigService;

    @ApiOperation("分组查询")
    @GetMapping("subgroup")
    @OperateLog(BusinessName = "配置管理-分组查询", operateType = OperateTypeEnum.SUBGROUP)
    public Result subgroup(@RequestBody @Validated QuerySystemConfigDTO queryParam) {
        return Result.success();
    }

    @ApiOperation("新增")
    @PostMapping
    @OperateLog(BusinessName = "配置管理-新增", operateType = OperateTypeEnum.ADD)
    public Result add(@RequestBody @Validated(ValidatedGroup.add.class) AddSystemConfigDTO saveParam) {

        return systemConfigService.add(saveParam) ? Result.success() : Result.fail();
    }

    @ApiOperation("删除")
    @DeleteMapping
    @OperateLog(BusinessName = "配置管理-删除", operateType = OperateTypeEnum.DELETE)
    public Result delete(@RequestBody @Validated(ValidatedGroup.delete.class) CommonDeleteParam deleteParam) {
        return systemConfigService.delete(deleteParam) ? Result.success() : Result.fail();
    }

    @ApiOperation("编辑")
    @PutMapping
    @OperateLog(BusinessName = "配置管理-编辑", operateType = OperateTypeEnum.UPDATE)
    public Result update(@RequestBody @Validated List<UpdateConfigDTO> updateParam) {

        return systemConfigService.update(updateParam) ? Result.success() : Result.fail();
    }

}
