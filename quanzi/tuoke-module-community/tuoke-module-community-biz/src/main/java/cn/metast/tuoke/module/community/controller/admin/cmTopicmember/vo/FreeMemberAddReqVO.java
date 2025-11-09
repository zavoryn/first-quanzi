package cn.metast.tuoke.module.community.controller.admin.cmTopicmember.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "管理后台 - 添加免费会员 Request VO")
@Data
public class FreeMemberAddReqVO {

    @Schema(description = "圈子ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "圈子ID不能为空")
    private Long topicId;

    @Schema(description = "用户ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    @Schema(description = "用户姓名", example = "张三")
    private String userName;

    @Schema(description = "付费时长(0一月 1两月 2三月 3四月 4五月 5半年)", requiredMode = Schema.RequiredMode.REQUIRED, example = "5")
    @NotNull(message = "付费时长不能为空")
    private Integer type;
}
