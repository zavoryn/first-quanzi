package cn.metast.tuoke.module.live.enums;

import cn.metast.tuoke.framework.common.exception.ErrorCode;
/**
 * Trade 错误码枚举类
 * trade 系统，使用 1-011-000-000 段
 *
 * @author LeeYan9
 * @since 2022-08-26
 */
public interface ErrorCodeConstants {

    // ========== Order 模块 1-011-000-000 ==========
    ErrorCode SNS_ABLUM_NOT_EXISTS = new ErrorCode(1-011-000-100, "相册信息不存在");
    ErrorCode SNS_ACT_NOT_EXISTS = new ErrorCode(1-011-000-200, "活动详情不存在");
    ErrorCode SNS_ACT_ACHIEVEMENT_NOT_EXISTS = new ErrorCode(1-011-000-300, "活动-成绩不存在");
    ErrorCode SNS_ACT_FILE_NOT_EXISTS = new ErrorCode(1-011-000-400, "活动资料不存在");
    ErrorCode SNS_ACT_GUEST_NOT_EXISTS = new ErrorCode(1-011-000-500, "活动嘉宾不存在");
    ErrorCode SNS_ACT_INFO_CFG_NOT_EXISTS = new ErrorCode(1-011-000-600, "报名填写信息设置不存在");
    ErrorCode SNS_ACT_INFO_TEMPLATE_NOT_EXISTS = new ErrorCode(1-011-000-700, "活动模板不存在");
    ErrorCode SNS_ACT_INFO_USER_NOT_EXISTS = new ErrorCode(1-011-000-800, "报名填写用户信息不存在");
    ErrorCode SNS_ACT_NOTE_NOT_EXISTS = new ErrorCode(1-011-000-900, "活动记录不存在");
    ErrorCode SNS_ACT_NOTICE_NOT_EXISTS = new ErrorCode(1-011-000-100, "活动公告不存在");
    ErrorCode SNS_ACT_PLAYER_USER_NOT_EXISTS = new ErrorCode(1-011-000-110, "参与人信息不存在");
    ErrorCode SNS_ACT_SCHEDULE_NOT_EXISTS = new ErrorCode(1-011-000-120, "活动日程不存在");
    ErrorCode SNS_ACT_USER_NOT_EXISTS = new ErrorCode(1-011-000-130, "活动报名人员不存在");
    ErrorCode SNS_APP_CONFIG_NOT_EXISTS = new ErrorCode(1-011-000-140, "配置不存在");
    ErrorCode SNS_COMMENT_NOT_EXISTS = new ErrorCode(1-011-000-150, "评论不存在");
    ErrorCode SNS_COMMENT_LIKE_NOT_EXISTS = new ErrorCode(1-011-000-160, "评论点赞人不存在");
    ErrorCode SNS_COMMENT_REPLY_NOT_EXISTS = new ErrorCode(1-011-000-170, "评论回复不存在");
    ErrorCode SNS_COMMENT_REPLY_LIKE_NOT_EXISTS = new ErrorCode(1-011-000-180, "评论回复点赞人数不存在");
    ErrorCode SNS_NEWS_NOT_EXISTS = new ErrorCode(1-011-000-190, "新闻信息不存在");
    ErrorCode SNS_POST_NOT_EXISTS = new ErrorCode(1-011-000-200, "动态信息不存在");
}
