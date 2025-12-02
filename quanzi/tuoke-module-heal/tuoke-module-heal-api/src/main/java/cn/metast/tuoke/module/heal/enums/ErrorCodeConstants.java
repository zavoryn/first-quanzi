package cn.metast.tuoke.module.heal.enums;

import cn.metast.tuoke.framework.common.exception.ErrorCode;

public interface ErrorCodeConstants {
    ErrorCode BANNER_NOT_EXISTS = new ErrorCode(10001, "首页banner不存在");
    ErrorCode HOME_CONFIG_NOT_EXISTS = new ErrorCode(10002, "首页模块配置不存在");
    ErrorCode KNOWLEDGE_NOT_EXISTS = new ErrorCode(10003, "健康知识不存在");
    ErrorCode SERVICE_NOT_EXISTS = new ErrorCode(10004, "服务列不存在");
    ErrorCode SERVICE_CONFIG_NOT_EXISTS = new ErrorCode(10005, "服务配置不存在");
    //档案 信息
    ErrorCode ARCHIVES_NOT_EXISTS = new ErrorCode(2_001_000_000, "档案信息不存在");
    ErrorCode ARCHIVES_USER_NOT_EXISTS = new ErrorCode(2_002_000_000, "档案信息关联用户不存在");
    ErrorCode DEVICE_NOT_EXISTS = new ErrorCode(2_003_000_000, "设备信息不存在");
    ErrorCode DEVICE_USER_NOT_EXISTS = new ErrorCode(2_004_000_000, "设备绑定用户信息不存在");
    ErrorCode DETECTION_NOT_EXISTS = new ErrorCode(2_005_000_000, "检测记录不存在");
}
