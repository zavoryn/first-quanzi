package cn.metast.tuoke.module.community.enums;

import cn.metast.tuoke.framework.common.exception.ErrorCode;

public interface ErrorCodeConstants {
    ErrorCode CM_CATEGORY_NOT_EXISTS = new ErrorCode(100110, "圈子分类不存在");
    ErrorCode CM_COLLECT_NOT_EXISTS = new ErrorCode(100111, "收藏记录不存在");
    ErrorCode CM_COMMENT_NOT_EXISTS = new ErrorCode(100112, "圈子评论不存在");
    ErrorCode CM_COMMENT_LIKE_NOT_EXISTS = new ErrorCode(100113, "评论点赞不存在");
    ErrorCode CM_COMMENT_THUMBS_NOT_EXISTS = new ErrorCode(100114, "评论用户关联不存在");
    ErrorCode CM_FOLLOW_NOT_EXISTS = new ErrorCode(100115, "用户关注中间不存在");
    ErrorCode CM_LINK_NOT_EXISTS = new ErrorCode(100116, "首页轮播图不存在");
    ErrorCode CM_MESSAGE_NOT_EXISTS = new ErrorCode(100117, "圈子消息不存在");
    ErrorCode CM_POST_NOT_EXISTS = new ErrorCode(100118, "用户发帖详情不存在");
    ErrorCode CM_POST_COLLECTION_NOT_EXISTS = new ErrorCode(100119, "用户帖子中间不存在");
    ErrorCode CM_TOPIC_NOT_EXISTS = new ErrorCode(1001110, "圈子详情不存在");
    ErrorCode CM_TOPIC_MEMBER_NOT_EXISTS = new ErrorCode(10011111, "圈子成员不存在");
    ErrorCode CM_POST_LIKE_NOT_EXISTS = new ErrorCode(10011112, "帖子点赞不存在");

    ErrorCode CM_BUY_LOG_NOT_EXISTS = new ErrorCode(10011113, "会员购买记录不存在");
    ErrorCode BUY_LOG_NOT_FOUND = new ErrorCode(1_004_013_002, "购买记录不存在");
    ErrorCode BUY_LOG_UPDATE_PAID_FAIL_PAY_ORDER_ID_ERROR = new ErrorCode(1_004_013_003, "购买记录更新支付状态失败，支付单编号不匹配");
    ErrorCode BUY_LOG_UPDATE_PAID_STATUS_NOT_UNPAID = new ErrorCode(1_004_013_004, "交易订单更新支付状态失败，订单不是【未支付】状态");
    ErrorCode BUY_LOG_UPDATE_PAID_FAIL_PAY_ORDER_STATUS_NOT_SUCCESS = new ErrorCode(1_004_013_005, "购买记录更新支付状态失败，支付单状态不是【支付成功】状态");
    ErrorCode BUY_LOG_UPDATE_PAID_FAIL_PAY_PRICE_NOT_MATCH = new ErrorCode(1_004_013_006, "购买记录更新支付状态失败，支付单金额不匹配");
    ErrorCode CM_BLOCK_NOT_EXISTS = new ErrorCode(1_004_013_007, "拉黑记录不存在");
    ErrorCode CM_REPORT_NOT_EXISTS = new ErrorCode(1_004_013_008, "举报记录不存在");

    ErrorCode CM_TOPIC_MEMBER_ALREADY_FREE = new ErrorCode(1_004_013_009, "该用户已是此圈子的免费会员");
    ErrorCode CM_TOPIC_FREE_MEMBER_LIMIT = new ErrorCode(1_004_013_010, "该圈子免费会员名额已满（最多2个）");
    ErrorCode CM_MESSAGE_WITHDRAW_NO_PERMISSION = new ErrorCode(1_004_013_011, "无撤回权限，仅圈主可操作");
    ErrorCode CM_MESSAGE_QUOTE_NO_PERMISSION = new ErrorCode(1_004_013_012, "无引用权限，仅圈主可操作");
    ErrorCode DIALOG_ANALYSIS_NOT_EXISTS = new ErrorCode(1_004_013_013, "对话分析不存在");

    // ========== 圈子配置 1_004_013_013==========
    ErrorCode CM_CONFIG_NOT_EXISTS = new ErrorCode(1_004_013_014, "圈子配置不存在");
    ErrorCode CM_CONFIG_DUPLICATE = new ErrorCode(1_004_013_015, "该部门-圈子配置已存在");
}
