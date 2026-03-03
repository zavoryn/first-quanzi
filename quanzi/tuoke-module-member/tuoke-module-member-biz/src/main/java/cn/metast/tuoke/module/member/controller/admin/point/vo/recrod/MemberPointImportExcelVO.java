package cn.metast.tuoke.module.member.controller.admin.point.vo.recrod;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 用户 Excel 导入 VO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = false) // 设置 chain = false，避免用户导入有问题
public class MemberPointImportExcelVO {

    @ExcelProperty("绑定手机号")
    private String mobile;

    @ExcelProperty("昵称")
    private String nickname;

    @ExcelProperty("可用积分")
    private Integer point;
    //1男2女
    @ExcelProperty("总积分")
    private Integer totalPoint;
    @ExcelProperty("用户ID")
    private String techUserId;
}
