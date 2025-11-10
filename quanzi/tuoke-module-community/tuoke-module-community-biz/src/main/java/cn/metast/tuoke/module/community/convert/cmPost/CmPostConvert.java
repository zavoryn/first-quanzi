package cn.metast.tuoke.module.community.convert.cmPost;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.util.collection.MapUtils;
import cn.metast.tuoke.framework.security.core.util.SecurityFrameworkUtils;
import cn.metast.tuoke.module.community.controller.admin.cmPost.vo.CmPostRespVO;
import cn.metast.tuoke.module.community.dal.dataobject.cmPost.CmPostDO;
import cn.metast.tuoke.module.community.dal.dataobject.cmPostlike.CmPostLikeDO;
import cn.metast.tuoke.module.community.service.cmComment.CmCommentService;
import cn.metast.tuoke.module.member.api.user.dto.MemberUserRespDTO;
import jakarta.annotation.Resource;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;
import java.util.Map;
import static cn.metast.tuoke.framework.common.util.collection.CollectionUtils.convertMap;

/**
 * 用户积分记录 Convert
 *
 * @author QingX
 */
@Mapper
public interface CmPostConvert {
    @Resource
    CmCommentService cmCommentService = null;

    CmPostConvert INSTANCE = Mappers.getMapper(CmPostConvert.class);

    default PageResult<CmPostRespVO> convertPage(PageResult<CmPostDO> pageResult, List<MemberUserRespDTO> users) {
        PageResult<CmPostRespVO> voPageResult = convertPage(pageResult);
        return voPageResult;
    }
    PageResult<CmPostRespVO> convertPage(PageResult<CmPostDO> pageResult);

}
