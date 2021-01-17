package com.geekalliance.taurus.core.params;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * CommonDeleteDTO
 * 通用删除模型
 *
 * @author geekeeper
 * @email geekkeeper@163.com
 * @date 2021-01-14 23:23
 */
@ApiModel(description = "删除参数")
@Data
public class CommonDeleteParam implements Serializable {
    @NotEmpty(message = "参数不能为空，请见ids参数")
    private List<String> ids;
}
