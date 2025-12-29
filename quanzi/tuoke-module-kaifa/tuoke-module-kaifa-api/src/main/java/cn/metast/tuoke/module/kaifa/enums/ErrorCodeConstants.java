package cn.metast.tuoke.module.kaifa.enums;

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
    ErrorCode AUTOMATED_NOT_EXISTS = new ErrorCode(1_011_000_010, "数据不存在");
    ErrorCode AUTOMATED_NOT_EXISTS_MAIL = new ErrorCode(1_011_000_011, "发送失效，请联系管理员维护");
    ErrorCode AUTOMATED_NOT_EXISTS_SMM = new ErrorCode(1_011_000_012, "数据不存在");
    ErrorCode EX_PLATFORM_NOT_EXISTS = new ErrorCode(1_011_000_013, "平台不存在");
    ErrorCode EX_PLATFORM_TENANT_NOT_EXISTS = new ErrorCode(1_011_000_014, "平台租户授权不存在");
    ErrorCode EX_ACCOUNT_NOT_EXISTS = new ErrorCode(1_011_000_015, "账号不存在");
    ErrorCode EX_EXECUTE_RECORD_NOT_EXISTS = new ErrorCode(1_011_000_016, "下发记录不存在");

}
