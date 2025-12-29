package cn.metast.tuoke.module.kaifa.enums;

/**
 * 社媒 操作日志枚举
 * 目的：统一管理，也减少 Service 里各种“复杂”字符串
 *
 * @author HUIHUI
 */
public interface LogRecordConstants {

    // ======================= 社媒 =======================
    String KAIFA_TYPE = "社媒";
    String KAIFA_CREATE_SUB_TYPE = "创建社媒";
    String KAIFA_CREATE_SUCCESS = "创建了社媒{{#exAccount.nickname}}";
    String KAIFA_UPDATE_SUB_TYPE = "更新社媒";
    String KAIFA_UPDATE_SUCCESS = "更新了社媒【{{#nickname}}】: {_DIFF{#updateReq}}";
    String KAIFA_DELETE_SUB_TYPE = "删除社媒";
    String KAIFA_DELETE_SUCCESS = "删除了社媒【{{#nickname}}】";
}
