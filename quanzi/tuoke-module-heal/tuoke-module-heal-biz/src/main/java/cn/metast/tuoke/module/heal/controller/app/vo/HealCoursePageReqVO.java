package cn.metast.tuoke.module.heal.controller.app.vo;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
@Schema(description = "管理后台 - 健康知识分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class HealCoursePageReqVO extends PageParam {

    /** id */
    protected Long id;

    /** 课程分类 */
    protected String type;

    /** 名称 */
    protected String name;

    /** 封面 */
    protected String logoUrl;

    /** 平台分类 */
    protected Long typeId;
    protected Long[] typeIds;
    protected String typeName;

    protected String[] codes;

    /** 课程介绍 */
    protected String introduce;

    /** 内容 */
    protected String content;

    /** 内容 */
    protected Integer visitNum;

    /** 内容 */
    protected Integer watchNum;

    /** 内容 */
    protected Integer commentNum;

    /** 内容 */
    protected Integer favoriteMum;

    /** 内容 */
    protected Integer likeNum;

}
