package cn.metast.tuoke.module.heal.controller.app.vo;
import lombok.Data;
import java.io.Serial;
/**
 * 课程 持久化对象
 *
 * @author metast
 */
@Data
public class HealCourseDo {

    @Serial
    private static final long serialVersionUID = 1L;

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
