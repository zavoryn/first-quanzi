package cn.metast.tuoke.module.live.service.snsAblum;

import cn.metast.tuoke.module.live.controller.admin.snsPost.vo.SnsPostRespVO;
import cn.metast.tuoke.module.live.controller.admin.snsPost.vo.SnsPostSaveReqVO;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.metast.tuoke.module.live.controller.admin.snsAblum.vo.*;
import cn.metast.tuoke.module.live.dal.dataobject.snsAblum.SnsAblumDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;

import cn.metast.tuoke.module.live.dal.mysql.snsAblum.SnsAblumMapper;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.module.live.enums.ErrorCodeConstants.*;

/**
 * 相册信息 Service 实现类
 *
 * @author 夏兆金
 */
@Service
@Validated
public class SnsAblumServiceImpl implements SnsAblumService {

    @Resource
    private SnsAblumMapper snsAblumMapper;

    @Override
    public Long createSnsAblum(SnsAblumSaveReqVO createReqVO) {
        // 插入
        SnsAblumDO snsAblum = BeanUtils.toBean(createReqVO, SnsAblumDO.class);
        snsAblumMapper.insert(snsAblum);
        // 返回
        return snsAblum.getId();
    }

    @Override
    public Boolean insertSnsAblum(SnsPostRespVO snsPost) {
        List<SnsAblumDO> snsAblumList=new ArrayList<SnsAblumDO>();
        if(!CollectionUtils.isEmpty(snsPost.getPicUrl())){
            List<Map<String,Object>> msp=snsPost.getPicUrl();
            if(!CollectionUtils.isEmpty(msp)) {
                for(Map < String,Object > str:msp){
                    SnsAblumDO snsAblum=new SnsAblumDO();
                    snsAblum.setPostId(snsPost.getPostId());
                    snsAblum.setUrl(str.get("url").toString());
                    snsAblumList.add(snsAblum);
                }
            }
        }
        if(!CollectionUtils.isEmpty(snsAblumList)){
            return snsAblumMapper.insertBatch(snsAblumList);
        }
        return false;
    }

    @Override
    public void updateSnsAblum(SnsAblumSaveReqVO updateReqVO) {
        // 校验存在
        validateSnsAblumExists(updateReqVO.getId());
        // 更新
        SnsAblumDO updateObj = BeanUtils.toBean(updateReqVO, SnsAblumDO.class);
        snsAblumMapper.updateById(updateObj);
    }

    @Override
    public void deleteSnsAblum(Long id) {
        // 校验存在
        validateSnsAblumExists(id);
        // 删除
        snsAblumMapper.deleteById(id);
    }

    private void validateSnsAblumExists(Long id) {
        if (snsAblumMapper.selectById(id) == null) {
            throw exception(SNS_ABLUM_NOT_EXISTS);
        }
    }

    @Override
    public SnsAblumDO getSnsAblum(Long id) {
        return snsAblumMapper.selectById(id);
    }

    @Override
    public PageResult<SnsAblumDO> getSnsAblumPage(SnsAblumPageReqVO pageReqVO) {
        return snsAblumMapper.selectPage(pageReqVO);
    }

    @Override
    public int deleteSnsAblumById(Long id) {
        return snsAblumMapper.deleteSnsAblumById(id);
    }

    @Override
    public Boolean insertSnsAblumList(SnsPostSaveReqVO snsPost) {
        List<SnsAblumDO> snsAblumList=new ArrayList<SnsAblumDO>();
        if(!CollectionUtils.isEmpty(snsPost.getPicUrl())){
            List<Map<String,Object>> msp=snsPost.getPicUrl();
            if(!CollectionUtils.isEmpty(msp)) {
                for(Map < String,Object > str:msp){
                    SnsAblumDO snsAblum=new SnsAblumDO();
                    snsAblum.setPostId(snsPost.getPostId());
                    snsAblum.setUrl(str.get("url").toString());
                    snsAblumList.add(snsAblum);
                }
            }
        }
        if(!CollectionUtils.isEmpty(snsAblumList)){
            return snsAblumMapper.insertBatch(snsAblumList);
        }
        return false;
    }

    @Override
    public List<SnsAblumDO>  selectSnsAblumPostId(Long postId) {
        return snsAblumMapper.selectSnsAblumPostId(postId);
    }

}
