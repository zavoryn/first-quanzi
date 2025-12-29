package cn.metast.tuoke.module.kaifa.controller.admin.email.emailsetting.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 公共配置 Response VO")
@Data
@ExcelIgnoreUnannotated
public class EmailSettingRespVO {

    @Schema(description = "Id", requiredMode = Schema.RequiredMode.REQUIRED, example = "32222")
    @ExcelProperty("Id")
    private Long id;

    @Schema(description = "发送类型(1-立即发送 2-定时)", example = "1")
    @ExcelProperty("发送类型(1-立即发送 2-定时)")
    private String sendType;

    @Schema(description = "定时发送时间")
    @ExcelProperty("定时发送时间")
    private String regularTime;

    @Schema(description = "双休日是否发送(1-是)")
    @ExcelProperty("双休日是否发送(1-是)")
    private String sendRestDay;

    @Schema(description = "过滤邮箱")
    @ExcelProperty("过滤邮箱")
    private String filterateEmail;

    @Schema(description = "优先发送对象")
    @ExcelProperty("优先发送对象")
    private String firstSend;

    @Schema(description = "单个公司邮箱开发上限 (0-不限制 1-限制)")
    @ExcelProperty("单个公司邮箱开发上限 (0-不限制 1-限制)")
    private String singleUpperLimit;

    @Schema(description = "过滤我的线索 (1-有效)")
    @ExcelProperty("过滤我的线索 (1-有效)")
    private String filterateMyClub;

    @Schema(description = "过滤同事线索 (1-有效)")
    @ExcelProperty("过滤同事线索 (1-有效)")
    private String filterateColleagueClub;

    @Schema(description = "过滤我的客户 (1-有效)")
    @ExcelProperty("过滤我的客户 (1-有效)")
    private String filterateMyCustomer;

    @Schema(description = "过滤同事客户 (1-有效)")
    @ExcelProperty("过滤同事客户 (1-有效)")
    private String filterateTsCustomer;

    @Schema(description = "过滤公海客户 (1-有效)")
    @ExcelProperty("过滤公海客户 (1-有效)")
    private String filteratePublicCustomer;

    @Schema(description = "过滤已发送 (1-有效)")
    @ExcelProperty("过滤已发送 (1-有效)")
    private String filterateBeenSent;

    @Schema(description = "筛选公司")
    @ExcelProperty("筛选公司")
    private String screenCompany;

    @Schema(description = "筛选KP职位")
    @ExcelProperty("筛选KP职位")
    private String screenPositionKp;

    @Schema(description = "筛选其他职位")
    @ExcelProperty("筛选其他职位")
    private String screenPositionOther;

    @Schema(description = "筛选邮箱状态")
    @ExcelProperty("筛选邮箱状态")
    private String screenEmail;

    @Schema(description = "存储公司")
    @ExcelProperty("存储公司")
    private String saveCompany;

    @Schema(description = "存储KP职位")
    @ExcelProperty("存储KP职位")
    private String savePositionKp;

    @Schema(description = "存储其他职位")
    @ExcelProperty("存储其他职位")
    private String savePositionOther;

    @Schema(description = "存储邮箱状态")
    @ExcelProperty("存储邮箱状态")
    private String saveEmail;

    @Schema(description = "存储标签")
    @ExcelProperty("存储标签")
    private String saveTags;

    @Schema(description = "默认循环")
    @ExcelProperty("默认循环")
    private String sopNo;

    @Schema(description = "循环开始日期-暂时不填，默认第二天的日期")
    @ExcelProperty("循环开始日期-暂时不填，默认第二天的日期")
    private String planTime;

    @Schema(description = "循环次数")
    @ExcelProperty("循环次数")
    private Long planNum;

    @Schema(description = "间隔天数")
    @ExcelProperty("间隔天数")
    private Long planDay;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
