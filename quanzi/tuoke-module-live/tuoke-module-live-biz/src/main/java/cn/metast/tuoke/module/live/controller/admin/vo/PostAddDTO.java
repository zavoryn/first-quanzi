package cn.metast.tuoke.module.live.controller.admin.vo;
import cn.metast.tuoke.module.live.controller.admin.snsPost.vo.SnsPostRespVO;
import lombok.Data;

@Data
public class PostAddDTO extends SnsPostRespVO {

    //图片路径pic identifiers数组
    private String[] picIdentifiers;
    //图片路径pic identifiersDelete删除数组
    private String[] identifiersDelete;

    //音视频路径 identifiers
    private String mediaIdentifiers;

    //文件路径 identifiers
    private String fileIdentifiers;
    //封面链接
    private String coverUrl;

    // 模块类型 智库1和校友2, 服务1和创投2,  5:兰杉学院banner  6:叁号路基金banner  7:兰杉企业研究会banner  8:紫金助企banner
    private String spType;

}
