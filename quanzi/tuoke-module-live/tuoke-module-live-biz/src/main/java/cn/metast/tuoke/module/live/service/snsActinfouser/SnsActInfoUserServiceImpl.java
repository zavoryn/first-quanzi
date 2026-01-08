package cn.metast.tuoke.module.live.service.snsActinfouser;

import cn.metast.tuoke.module.live.controller.admin.snsAct.vo.SnsActRespVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.metast.tuoke.module.live.controller.admin.snsActinfouser.vo.*;
import cn.metast.tuoke.module.live.dal.dataobject.snsActinfouser.SnsActInfoUserDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;

import cn.metast.tuoke.module.live.dal.mysql.snsActinfouser.SnsActInfoUserMapper;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.module.live.enums.ErrorCodeConstants.*;

/**
 * 报名填写用户信息 Service 实现类
 *
 * @author 夏兆金
 */
@Service
@Validated
public class SnsActInfoUserServiceImpl implements SnsActInfoUserService {

    @Resource
    private SnsActInfoUserMapper snsActInfoUserMapper;

    @Override
    public Long createSnsActInfoUser(SnsActInfoUserSaveReqVO createReqVO) {
        // 插入
        SnsActInfoUserDO snsActInfoUser = BeanUtils.toBean(createReqVO, SnsActInfoUserDO.class);
        snsActInfoUserMapper.insert(snsActInfoUser);
        // 返回
        return snsActInfoUser.getId();
    }

    @Override
    public void updateSnsActInfoUser(SnsActInfoUserSaveReqVO updateReqVO) {
        // 校验存在
        validateSnsActInfoUserExists(updateReqVO.getId());
        // 更新
        SnsActInfoUserDO updateObj = BeanUtils.toBean(updateReqVO, SnsActInfoUserDO.class);
        snsActInfoUserMapper.updateById(updateObj);
    }

    @Override
    public void deleteSnsActInfoUser(Long id) {
        // 校验存在
        validateSnsActInfoUserExists(id);
        // 删除
        snsActInfoUserMapper.deleteById(id);
    }

    private void validateSnsActInfoUserExists(Long id) {
        if (snsActInfoUserMapper.selectById(id) == null) {
            throw exception(SNS_ACT_INFO_USER_NOT_EXISTS);
        }
    }

    @Override
    public SnsActInfoUserDO getSnsActInfoUser(Long id) {
        return snsActInfoUserMapper.selectById(id);
    }

    @Override
    public PageResult<SnsActInfoUserDO> getSnsActInfoUserPage(SnsActInfoUserPageReqVO pageReqVO) {
        return snsActInfoUserMapper.selectPage(pageReqVO);
    }

    @Override
    public int insertSnsActInfoUserList(List<SnsActInfoUserRespVO> actInfoUsers) {
        return snsActInfoUserMapper.insertSnsActInfoUserList(actInfoUsers);
    }

    @Override
    public PageResult<SnsActInfoUserDO> selectSnsActInfoUserList(SnsActInfoUserPageReqVO pageReqVO) {
        IPage<SnsActInfoUserDO> page = new Page<>(pageReqVO.getPageNo(), pageReqVO.getPageSize());
        snsActInfoUserMapper.selectSnsActInfoUserList(page, pageReqVO);
        return new PageResult<>(page.getRecords(), page.getTotal());
    }

}
