package com.jungle.serviceedu.entity.chapter;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VideoVo {
    private String id;
    private String title;
    @ApiModelProperty(value = "云服务器上存储的视频文件名称")
    private String videoOriginalName;
    private String videoSourceId;
}
