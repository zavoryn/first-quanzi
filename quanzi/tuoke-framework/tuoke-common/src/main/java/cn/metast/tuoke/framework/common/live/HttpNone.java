package cn.metast.tuoke.framework.common.live;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "com.kalacheng.libbas.model.HttpNone", description = "没有实际业务用途，在API不需要反复内容时的占位用。")
public class HttpNone implements Serializable {

    public static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "没有用的东西")
    public String no_use;
}
