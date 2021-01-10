package com.geekalliance.taurus.base.organization.controller;

import com.geekalliance.taurus.web.controller.BaseController;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
