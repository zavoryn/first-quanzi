package cn.metast.tuoke.module.live.controller.admin.snsActnotice.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 活动公告 Response VO")
@Data
@ExcelIgnoreUnannotated
public class SnsActNoticeRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "23946")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "act_id", requiredMode = Schema.RequiredMode.REQUIRED, example = "7203")
    @ExcelProperty("act_id")
    private Long actId;

    @Schema(description = "公共标题")
    @ExcelProperty("公共标题")
    private String title;

    @Schema(description = "时间")
    @ExcelProperty("时间")
    private LocalDateTime time;

    @Schema(description = "公告内容")
    @ExcelProperty("公告内容")
    private String context;

    @Schema(description = "公告类型（1通知 2公告）", example = "1")
    @ExcelProperty("公告类型（1通知 2公告）")
    private String noticeType;

    @Schema(description = "公告状态（0正常 1关闭）", example = "1")
    @ExcelProperty("公告状态（0正常 1关闭）")
    private String status;

    @Schema(description = "re")
    @ExcelProperty("re")
    private String re;

    @Schema(description = "发送用户的id", requiredMode = Schema.RequiredMode.REQUIRED, example = "26156")
    @ExcelProperty("发送用户的id")
    private String fromId;

    @Schema(description = "接收用户的id", example = "29471")
    @ExcelProperty("接收用户的id")
    private String toId;

    @Schema(description = "single 单个 some  特定人  all 全部")
    @ExcelProperty("single 单个 some  特定人  all 全部")
    private String scope;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}