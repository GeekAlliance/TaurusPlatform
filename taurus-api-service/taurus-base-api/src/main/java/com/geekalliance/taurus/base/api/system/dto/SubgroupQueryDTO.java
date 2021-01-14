package com.geekalliance.taurus.base.api.system.dto;

import com.geekalliance.taurus.core.entity.ValidatedGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * SubgroupQueryDTO
 * 系统设置分组查询
 *
 * @author geekeeper
 * @email geekkeeper@163.com
 * @date 2021-01-14 22:58
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SubgroupQueryDTO extends ValidatedGroup {
    /**
     * 应用编号
     */
    @NotBlank(message = "应用编号不能为空，请检查application参数")
    private String application;
    /**
     * 配置组
     */
    @NotBlank(message = "配置组不能为空，请检查subgroup参数")
    private String subgroup;

}
