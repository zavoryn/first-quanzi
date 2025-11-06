package cn.metast.tuoke.module.community.api.cmTopic.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 圈子成员响应 DTO
 *
 * @author metast.cn
 */
@Data
public class TopicMemberRespDTO {

    /**
     * 成员记录ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 圈子ID
     */
    private Long topicId;

    /**
     * 圈子名称（软件版本）
     */
    private String topicName;

    /**
     * 合同开始日期
     */
    private LocalDateTime startTime;

    /**
     * 合同结束日期
     */
    private LocalDateTime endTime;
}
