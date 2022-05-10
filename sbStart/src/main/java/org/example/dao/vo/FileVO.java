package org.example.dao.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("文件")
public class FileVO {
    @ApiModelProperty(value = "文件ID")
    private Long id;
    @ApiModelProperty(value = "源文件名")
    private String oldName;
    @ApiModelProperty(value = "现文件名")
    private String name;
    @ApiModelProperty(value = "文件类型")
    private String suffix;
    @ApiModelProperty(value = "访问地址")
    private String url;
}
