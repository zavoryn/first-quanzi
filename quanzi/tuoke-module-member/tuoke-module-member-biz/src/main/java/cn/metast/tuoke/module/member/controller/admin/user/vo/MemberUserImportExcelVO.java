package cn.metast.tuoke.module.member.controller.admin.user.vo;

import cn.metast.tuoke.framework.excel.core.annotations.DictFormat;
import cn.metast.tuoke.framework.excel.core.convert.DictConvert;
import cn.metast.tuoke.module.system.enums.DictTypeConstants;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 用户 Excel 导入 VO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = false) // 设置 chain = false，避免用户导入有问题
public class MemberUserImportExcelVO {

    @ExcelProperty("账户绑定手机号")
    private String mobile;

    @ExcelProperty("昵称")
    private String nickname;

    @ExcelProperty("姓名")
    private String name;
    //1男2女
    @ExcelProperty("性别")
    private String sex;

    @ExcelProperty("最近访问时间")
    private String loginDate;

    @ExcelProperty("生日")
    private String birthday;

    @ExcelProperty("总积分")
    private Integer point;

    @ExcelProperty("备注名")
    private String mark;

    @ExcelProperty("来源渠道")
    private String registerTerminal;
    @ExcelProperty("用户ID")
    private String techUserId;

    //--------------下面是没传的字段-----------------
    //private String password;
    //private Byte status;
    //private String registerIp;
    //private String avatar;
    //private String loginIp;


}
