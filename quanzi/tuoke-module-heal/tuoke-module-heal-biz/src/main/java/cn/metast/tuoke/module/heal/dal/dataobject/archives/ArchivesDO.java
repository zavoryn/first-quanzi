package cn.metast.tuoke.module.heal.dal.dataobject.archives;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.metast.tuoke.framework.mybatis.core.dataobject.BaseDO;

/**
 * 档案信息 DO
 *
 * @author 超级管理员
 */
@TableName("wn_archives")
@KeySequence("wn_archives_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArchivesDO extends BaseDO {

    /**
     * ID
     */
    @TableId
    private Long id;
    /**
     * 用户ID
     */
    private Long uid;
    @TableField(exist = false)
    private String uname;
    /**
     * 姓名
     */
    private String name;
    /**
     * 电话
     */
    private String phone;
    /**
     * 年龄
     */
    private Long age;
    /**
     * 行别
     */
    private String sex;
    /**
     * 身份证号码
     */
    private String idcard;
    /**
     * 生日
     */
    private String birthday;
    /**
     * 体重
     */
    private Double weight;
    /**
     * 身高
     */
    private Double height;
    /**
     * 血型
     */
    private String blood;
    /**
     * 过敏反应
     */
    private String gmfy;
    /**
     * 家族病史
     */
    private String jzbs;
    /**
     * 紧急联系人
     */
    private String jjlxr;
    /**
     * 体检报告
     */
    private String reportTj;
    /**
     * 病例
     */
    private String reportBl;
    /**
     * 图片序号
     */
    private Integer imgid;

}
