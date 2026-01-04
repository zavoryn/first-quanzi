package cn.metast.tuoke.module.live.controller.admin.vo;

import lombok.Data;

import java.util.List;

/**
 * 评论
 */
@Data
public class CommentVo {
    //评论id
    private long id;
    //评论内容
    private String content;
    //时间
    private String createTime;
    //是否点赞
    private String isLike;
    //点赞个数
    private Integer likeNum;

    private Long userId;
    //联系人聊天ID
    private String imuserId;
    //名称
    private String nickName;
    //联系人头像地址
    private String avatar;
    //电话
    private String phone;
    //评论的评论
    List<CommentVo> subCommentInfo;

}
