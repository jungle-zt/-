package com.jungle.serviceedu.entity.member;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UcenterMemberPay {
    @ApiModelProperty(value = "用户id")
    private String id;
    @ApiModelProperty(value = "昵称")
    private String nickname;
    @ApiModelProperty(value = "用户头像")
    private String avatar;
}
