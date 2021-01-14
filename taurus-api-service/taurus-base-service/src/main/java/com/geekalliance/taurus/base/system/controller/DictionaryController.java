package com.geekalliance.taurus.base.system.controller;

import com.geekalliance.taurus.base.system.service.DictionaryService;
import com.geekalliance.taurus.base.system.service.DictionaryTypeService;
import com.geekalliance.taurus.web.controller.BaseController;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
