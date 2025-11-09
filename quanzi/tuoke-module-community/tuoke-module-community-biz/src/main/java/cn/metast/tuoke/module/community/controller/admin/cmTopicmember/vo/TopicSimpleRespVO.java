package cn.metast.tuoke.module.community.controller.admin.cmTopicmember.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 圈子简单信息 Response VO")
@Data
public class TopicSimpleRespVO {

    @Schema(description = "圈子ID", example = "1")
    private Long id;

    @Schema(description = "圈子名称", example = "投资交流圈")
    private String topicName;
}