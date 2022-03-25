package com.jungle.commonUtils.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UcenterMember {
    @ApiModelProperty(value = "用户id")
    private String id;
    @ApiModelProperty(value = "昵称")
    private String nickname;
    @ApiModelProperty(value = "用户头像")
    private String avatar;
    @ApiModelProperty(value = "手机号")
    private String mobile;
}
