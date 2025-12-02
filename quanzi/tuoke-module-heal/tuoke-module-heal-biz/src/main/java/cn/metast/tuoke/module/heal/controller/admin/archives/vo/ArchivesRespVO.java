package cn.metast.tuoke.module.heal.controller.admin.archives.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 档案信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ArchivesRespVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "23748")
    @ExcelProperty("ID")
    private Long id;

    @Schema(description = "用户ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "3829")
    private Long uid;
    @ExcelProperty("用户")
    private String uname;

    @Schema(description = "姓名", example = "王五")
    @ExcelProperty("姓名")
    private String name;

    @Schema(description = "电话")
    @ExcelProperty("电话")
    private String phone;

    @Schema(description = "年龄")
    @ExcelProperty("年龄")
    private Long age;

    @Schema(description = "行别")
    @ExcelProperty("行别")
    private String sex;

    @Schema(description = "身份证号码")
    @ExcelProperty("身份证号码")
    private String idcard;

    @Schema(description = "生日")
    @ExcelProperty("生日")
    private String birthday;

    @Schema(description = "体重")
    @ExcelProperty("体重")
    private Double weight;

    @Schema(description = "身高")
    @ExcelProperty("身高")
    private Double height;

    @Schema(description = "血型")
    @ExcelProperty("血型")
    private String blood;

    @Schema(description = "过敏反应")
    @ExcelProperty("过敏反应")
    private String gmfy;

    @Schema(description = "家族病史")
    @ExcelProperty("家族病史")
    private String jzbs;

    @Schema(description = "紧急联系人")
    @ExcelProperty("紧急联系人")
    private String jjlxr;

    @Schema(description = "体检报告")
    @ExcelProperty("体检报告")
    private String reportTj;

    @Schema(description = "病例")
    @ExcelProperty("病例")
    private String reportBl;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "图片序号", example = "106")
    @ExcelProperty("图片序号")
    private Integer imgid;

}
