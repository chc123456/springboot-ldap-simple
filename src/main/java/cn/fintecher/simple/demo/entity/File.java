package cn.fintecher.simple.demo.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by ChenChang on 2017/8/12.
 */
@Data
public class File implements Serializable {
    @ApiModelProperty(notes = "文件原始名称")
    private String originalName;
    @ApiModelProperty(notes = "文件url地址")
    private String url;
    @ApiModelProperty(notes = "调用OSS SDK 传入的objectKey")
    private String objectKey;

}
