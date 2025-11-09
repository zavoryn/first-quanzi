package cn.metast.tuoke.module.community.controller.admin.dialog.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 对话分析 Response VO")
@Data
@ExcelIgnoreUnannotated
public class DialogAnalysisRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "23262")
    @ExcelProperty("主键ID")
    private Long id;

    @Schema(description = "关联圈子ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "8470")
    @ExcelProperty("关联圈子ID")
    private Long topicId;

    @Schema(description = "对话方1姓名")
    @ExcelProperty("对话方1姓名")
    private String userName1;

    @Schema(description = "对话方2姓名")
    @ExcelProperty("对话方2姓名")
    private String userName2;

    @Schema(description = "对话内容(纯文本)")
    @ExcelProperty("对话内容(纯文本)")
    private String dialogContent;

    @Schema(description = "原始HTML内容")
    @ExcelProperty("原始HTML内容")
    private String originalHtml;

    @Schema(description = "音频文件URL列表(JSON)")
    @ExcelProperty("音频文件URL列表(JSON)")
    private String audioUrls;

    @Schema(description = "分析结果(Markdown)")
    @ExcelProperty("分析结果(Markdown)")
    private String analysisResult;

    @Schema(description = "状态(0待处理 1进行中 2已完成 3失败)", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("状态(0待处理 1进行中 2已完成 3失败)")
    private Integer status;

    @Schema(description = "错误信息")
    @ExcelProperty("错误信息")
    private String errorMsg;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
