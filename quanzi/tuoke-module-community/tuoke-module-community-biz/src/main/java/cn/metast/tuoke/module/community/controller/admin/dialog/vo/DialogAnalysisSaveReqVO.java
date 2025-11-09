package cn.metast.tuoke.module.community.controller.admin.dialog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "管理后台 - 对话分析新增/修改 Request VO")
@Data
public class DialogAnalysisSaveReqVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "23262")
    private Long id;

    @Schema(description = "关联圈子ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "8470")
    @NotNull(message = "关联圈子ID不能为空")
    private Long topicId;

    @Schema(description = "对话方1姓名")
    private String userName1;

    @Schema(description = "对话方2姓名")
    private String userName2;

    @Schema(description = "对话内容(纯文本)")
    private String dialogContent;

    @Schema(description = "原始HTML内容")
    private String originalHtml;

    @Schema(description = "音频文件URL列表(JSON)")
    private String audioUrls;

    @Schema(description = "分析结果(Markdown)")
    private String analysisResult;

    @Schema(description = "状态(0待处理 1进行中 2已完成 3失败)", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "状态(0待处理 1进行中 2已完成 3失败)不能为空")
    private Integer status;

    @Schema(description = "错误信息")
    private String errorMsg;

}
