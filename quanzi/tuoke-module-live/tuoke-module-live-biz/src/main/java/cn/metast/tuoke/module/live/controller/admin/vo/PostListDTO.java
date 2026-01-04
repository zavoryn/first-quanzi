package cn.metast.tuoke.module.live.controller.admin.vo;
import lombok.Data;

@Data
//  "发布列表DTO" ,required = true)
public class PostListDTO {
    //rec 推荐  fri 朋友圈 focus 关注 fav 收藏 user 某用户的ID
    private  String reqType;
    //类型(dyna 动态 act  活动 bus 商机 ent 企业家 spe 智库 srv  服务,ablum相册)
    private  String postType;
    //用户ID
    private Long userId;

    //每页数据大小，默认10
    private Integer pageSize;
    //页码，从0开始
    private Integer pageNum;

    private String param;//1进行中0已结束
    private String searchKey;

    private Long postFlag;//1我的收藏，2我创建的

    private Integer mStatus;//判断活动是否是草稿

    private String spType; // 模块类型 智库1和校友2, 服务1和创投2,  5:兰杉学院banner  6:叁号路基金banner  7:兰杉企业研究会banner  8:紫金助企banner
}
