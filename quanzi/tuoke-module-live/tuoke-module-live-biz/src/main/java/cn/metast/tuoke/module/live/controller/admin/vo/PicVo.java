package cn.metast.tuoke.module.live.controller.admin.vo;

import lombok.Data;

@Data
public class PicVo {
    //图片id
    private Integer id;
    //缩略图地址
    private String thumbnail;
    //原图地址
    private String url;
    //图片类型
    private String type;
    private String identifier;
    private String userName;
    private Long userId;
}
