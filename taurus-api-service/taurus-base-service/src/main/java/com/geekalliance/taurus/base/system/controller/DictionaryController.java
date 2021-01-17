package com.geekalliance.taurus.base.system.controller;

import com.geekalliance.taurus.base.api.system.params.*;
import com.geekalliance.taurus.base.system.service.DictionaryService;
import com.geekalliance.taurus.base.system.service.DictionaryTypeService;
import com.geekalliance.taurus.core.annotion.OperateLog;
import com.geekalliance.taurus.core.entity.ValidatedGroup;
import com.geekalliance.taurus.core.enums.OperateTypeEnum;
import com.geekalliance.taurus.core.params.CommonDeleteParam;
import com.geekalliance.taurus.core.result.Result;
import com.geekalliance.taurus.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * DictionaryController
 *
 * @author geekeeper
 * @email geekkeeper@163.com
 * @date 2021-01-14 22:08
 */
@Api(value = "字典管理", tags = {"字典管理"})
@Slf4j
@RestController
@RequestMapping("/v1/dictionary")
public class DictionaryController extends BaseController {

    @Resource
    private DictionaryService dictionaryService;

    @Resource
    private DictionaryTypeService dictionaryTypeService;

    @ApiOperation("字典类型")
    @GetMapping("dictionaryTypes")
    @OperateLog(BusinessName = "字典类型-查询", operateType = OperateTypeEnum.QUERY)
    public Result getDictionaryTypes(@RequestBody @Validated QueryDictionaryTypeParam queryParam) {
        return Result.success(dictionaryTypeService.getDictionaryTypes(queryParam));
    }

    @ApiOperation("字典列表")
    @GetMapping("dictionaries")
    @OperateLog(BusinessName = "字典列表", operateType = OperateTypeEnum.QUERY)
    public Result getDictionaries(@RequestBody @Validated QueryDictionaryParam queryParam) {
        return Result.success(dictionaryTypeService.getDictionaries(queryParam));
    }

    @ApiOperation("新增")
    @PostMapping
    @OperateLog(BusinessName = "组织机构-新增", operateType = OperateTypeEnum.ADD)
    public Result add(@RequestBody @Validated(ValidatedGroup.add.class) AddDictionaryParam saveParam) {

        return dictionaryService.add(saveParam) ? Result.success() : Result.fail();
    }

    @ApiOperation("删除")
    @DeleteMapping
    @OperateLog(BusinessName = "组织机构-删除", operateType = OperateTypeEnum.DELETE)
    public Result delete(@RequestBody @Validated(ValidatedGroup.delete.class) CommonDeleteParam deleteParam) {
        return dictionaryService.delete(deleteParam) ? Result.success() : Result.fail();
    }

    @ApiOperation("编辑")
    @PutMapping
    @OperateLog(BusinessName = "组织机构-编辑", operateType = OperateTypeEnum.UPDATE)
    public Result update(@RequestBody @Validated(ValidatedGroup.update.class) UpdateDictionaryParam updateParam) {
        return dictionaryService.update(updateParam) ? Result.success() : Result.fail();
    }



}
