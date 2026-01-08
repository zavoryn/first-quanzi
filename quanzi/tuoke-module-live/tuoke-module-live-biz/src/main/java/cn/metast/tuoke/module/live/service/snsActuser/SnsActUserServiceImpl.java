package cn.metast.tuoke.module.live.service.snsActuser;

import cn.metast.tuoke.module.live.controller.admin.snsAct.vo.SnsActRespVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.metast.tuoke.module.live.controller.admin.snsActuser.vo.*;
import cn.metast.tuoke.module.live.dal.dataobject.snsActuser.SnsActUserDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;

import cn.metast.tuoke.module.live.dal.mysql.snsActuser.SnsActUserMapper;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.module.live.enums.ErrorCodeConstants.*;

/**
 * 活动报名人员 Service 实现类
 *
 * @author 夏兆金
 */
@Service
@Validated
public class SnsActUserServiceImpl implements SnsActUserService {

    @Resource
    private SnsActUserMapper snsActUserMapper;

    @Override
    public Long createSnsActUser(SnsActUserSaveReqVO createReqVO) {
        // 插入
        SnsActUserDO snsActUser = BeanUtils.toBean(createReqVO, SnsActUserDO.class);
        snsActUserMapper.insert(snsActUser);
        // 返回
        return snsActUser.getId();
    }

    @Override
    public void updateSnsActUser(SnsActUserSaveReqVO updateReqVO) {
        // 校验存在
        validateSnsActUserExists(updateReqVO.getId());
        // 更新
        SnsActUserDO updateObj = BeanUtils.toBean(updateReqVO, SnsActUserDO.class);
        snsActUserMapper.updateById(updateObj);
    }

    @Override
    public void deleteSnsActUser(Long id) {
        // 校验存在
        validateSnsActUserExists(id);
        // 删除
        snsActUserMapper.deleteById(id);
    }

    private void validateSnsActUserExists(Long id) {
        if (snsActUserMapper.selectById(id) == null) {
            throw exception(SNS_ACT_USER_NOT_EXISTS);
        }
    }

    @Override
    public SnsActUserDO getSnsActUser(Long id) {
        return snsActUserMapper.selectById(id);
    }

    @Override
    public PageResult<SnsActUserDO> getSnsActUserPage(SnsActUserPageReqVO pageReqVO) {
        return snsActUserMapper.selectPage(pageReqVO);
    }

    @Override
    public List<SnsActUserRespVO> selectSnsActUserAppList(SnsActUserRespVO snsActUser) {
        return snsActUserMapper.selectSnsActUserAppList(snsActUser);
    }

    @Override
    public PageResult<SnsActUserRespVO> selectSnsActUserList(SnsActUserPageReqVO pageReqVO) {
        IPage<SnsActUserRespVO> page = new Page<>(pageReqVO.getPageNo(), pageReqVO.getPageSize());
        snsActUserMapper.selectSnsActUserList(page, pageReqVO);
        return new PageResult<>(page.getRecords(), page.getTotal());
    }

    @Override
    public int insertSnsActUser(SnsActUserRespVO snsActUser) {
        return snsActUserMapper.insertSnsActUser(snsActUser);
    }

}
