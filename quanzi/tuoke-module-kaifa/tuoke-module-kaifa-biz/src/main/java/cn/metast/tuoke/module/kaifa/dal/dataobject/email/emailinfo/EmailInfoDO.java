package cn.metast.tuoke.module.kaifa.dal.dataobject.email.emailinfo;

import lombok.*;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.*;
import cn.metast.tuoke.framework.mybatis.core.dataobject.BaseDO;

/**
 * 邮件内容 DO
 *
 * @author 精卫拓客
 */
@TableName("jw_email_info")
@KeySequence("jw_email_info_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailInfoDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 类型(1-发送 2-接收 3-草稿 4-垃圾邮件 9-自动发送的)
     */
    private String type;
    /**
     * 主题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 文本内容（摘要）
     */
    private String contentText;
    /**
     * 附件（多个）
     */
    private String fileUrl;
    /**
     * 发送人
     */
    private String sendName;
    /**
     * 发送邮箱
     */
    private String sendEmail;
    /**
     * 接收人
     */
    private String receiveName;
    /**
     * 接收邮箱
     */
    private String receiveEmail;
    /**
     * 抄送邮箱
     */
    private String copyEmail;
    /**
     * 发送时间
     */
    private LocalDateTime sendTime;
    /**
     * 接收时间
     */
    private LocalDateTime receiveTime;
    /**
     * 处理时间
     */
    private LocalDateTime handTime;
    /**
     * 处理状态
     */
    private String handStatus;
    /**
     * 1-已读
     */
    private String isSee;
    /**
     * 是否置顶
     */
    private String isTop;
    /**
     * 置顶时间
     */
    private LocalDateTime topTime;
    /**
     * 文件夹
     */
    private Long folder;
    /**
     * 来源时区
     */
    private String source;
    /**
     * 来源更新时间
     */
    private LocalDateTime sourceTime;
    /**
     * 1-已点击
     */
    private String isClick;
    /**
     * 1-追踪
     */
    private String isTrack;
    /**
     * 打开次数
     */
    private Long openCount;
    /**
     * 已打开时间
     */
    private LocalDateTime openTime;
    /**
     * 打开国家
     */
    private String openCountry;
    /**
     * 点击次数
     */
    private Long clickCount;
    /**
     * 已点击时间
     */
    private LocalDateTime clickTime;
    /**
     * 点击国家
     */
    private String clickCountry;
    /**
     * 自动回复邮件
     */
    private String isRepaly;
    /**
     * 收到的客户回复内容
     */
    private String replayContent;
    /**
     * 上一步绑定的id,或者回复的id
     */
    private Long replayId;
    /**
     * 原生邮件id
     */
    private Long emailId;
    /**
     * 标签
     */
    private String tags;
    /**
     * 备注
     */
    private String remark;
    /**
     * 状态
     */
    private String status;

}
