package com.atguigu.yygh.vo.msm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "短信实体")
public class SmsVo {

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "短信模板id")
    private String templateId;

    @ApiModelProperty(value = "短信模板参数")
    private String[] param;
}
