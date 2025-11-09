package cn.metast.tuoke.module.community.controller.admin.dialog.vo;

import cn.metast.tuoke.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.metast.tuoke.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 对话分析分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DialogAnalysisPageReqVO extends PageParam {

    @Schema(description = "关联圈子ID", example = "8470")
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

    @Schema(description = "状态(0待处理 1进行中 2已完成 3失败)", example = "2")
    private Integer status;

    @Schema(description = "错误信息")
    private String errorMsg;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
