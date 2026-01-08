package cn.metast.tuoke.module.live.service.snsActplayeruser;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.metast.tuoke.module.live.controller.admin.snsActplayeruser.vo.*;
import cn.metast.tuoke.module.live.dal.dataobject.snsActplayeruser.SnsActPlayerUserDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;

import cn.metast.tuoke.module.live.dal.mysql.snsActplayeruser.SnsActPlayerUserMapper;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.module.live.enums.ErrorCodeConstants.*;

/**
 * 参与人信息 Service 实现类
 *
 * @author 夏兆金
 */
@Service
@Validated
public class SnsActPlayerUserServiceImpl implements SnsActPlayerUserService {

    @Resource
    private SnsActPlayerUserMapper snsActPlayerUserMapper;

    @Override
    public Long createSnsActPlayerUser(SnsActPlayerUserSaveReqVO createReqVO) {
        // 插入
        SnsActPlayerUserDO snsActPlayerUser = BeanUtils.toBean(createReqVO, SnsActPlayerUserDO.class);
        snsActPlayerUserMapper.insert(snsActPlayerUser);
        // 返回
        return snsActPlayerUser.getId();
    }

    @Override
    public void updateSnsActPlayerUser(SnsActPlayerUserSaveReqVO updateReqVO) {
        // 校验存在
        validateSnsActPlayerUserExists(updateReqVO.getId());
        // 更新
        SnsActPlayerUserDO updateObj = BeanUtils.toBean(updateReqVO, SnsActPlayerUserDO.class);
        snsActPlayerUserMapper.updateById(updateObj);
    }

    @Override
    public void deleteSnsActPlayerUser(Long id) {
        // 校验存在
        validateSnsActPlayerUserExists(id);
        // 删除
        snsActPlayerUserMapper.deleteById(id);
    }

    private void validateSnsActPlayerUserExists(Long id) {
        if (snsActPlayerUserMapper.selectById(id) == null) {
            throw exception(SNS_ACT_PLAYER_USER_NOT_EXISTS);
        }
    }

    @Override
    public SnsActPlayerUserDO getSnsActPlayerUser(Long id) {
        return snsActPlayerUserMapper.selectById(id);
    }

    @Override
    public PageResult<SnsActPlayerUserDO> getSnsActPlayerUserPage(SnsActPlayerUserPageReqVO pageReqVO) {
        return snsActPlayerUserMapper.selectPage(pageReqVO);
    }

    @Override
    public List<SnsActPlayerUserRespVO> selectSnsActPlayerUserList(SnsActPlayerUserRespVO snsActPlayerUser) {
        return snsActPlayerUserMapper.selectSnsActPlayerUserList(snsActPlayerUser);
    }

    @Override
    public int insertSnsActPlayerUserList(List<SnsActPlayerUserRespVO> snsActPlayerUsers) {
        return snsActPlayerUserMapper.insertSnsActPlayerUserList(snsActPlayerUsers);
    }

}
