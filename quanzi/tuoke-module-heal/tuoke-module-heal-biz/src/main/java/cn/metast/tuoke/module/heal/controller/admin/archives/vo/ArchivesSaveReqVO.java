package cn.metast.tuoke.module.heal.controller.admin.archives.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 档案信息新增/修改 Request VO")
@Data
public class ArchivesSaveReqVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "23748")
    private Long id;

    @Schema(description = "用户ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "3829")
    private Long uid;

    @Schema(description = "姓名", example = "王五")
    private String name;

    @Schema(description = "电话")
    private String phone;

    @Schema(description = "年龄")
    private Long age;

    @Schema(description = "行别")
    private String sex;

    @Schema(description = "身份证号码")
    private String idcard;

    @Schema(description = "生日")
    private String birthday;

    @Schema(description = "体重")
    private Double weight;

    @Schema(description = "身高")
    private Double height;

    @Schema(description = "血型")
    private String blood;

    @Schema(description = "过敏反应")
    private String gmfy;

    @Schema(description = "家族病史")
    private String jzbs;

    @Schema(description = "紧急联系人")
    private String jjlxr;

    @Schema(description = "体检报告")
    private String reportTj;

    @Schema(description = "病例")
    private String reportBl;

    @Schema(description = "图片序号", example = "106")
    private Integer imgid;

}
