package cn.metast.tuoke.module.live.controller.admin.vo;
import lombok.Data;

import java.util.Map;

@Data
//  "发布列表DTO" ,required = true)
public class ActListDTO extends PostListDTO{
    //开始日期
    private String  startTime;
    //结束日期
    private String  endTime;
    //后台判断活动状态
    private Long flag;

    private String actType;

    private Map<String, Object> params;//存放时间筛选项
}
