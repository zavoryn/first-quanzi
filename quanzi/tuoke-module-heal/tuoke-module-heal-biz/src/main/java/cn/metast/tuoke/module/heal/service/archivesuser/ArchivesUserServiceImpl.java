package cn.metast.tuoke.module.heal.service.archivesuser;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import cn.metast.tuoke.module.heal.controller.admin.archivesuser.vo.*;
import cn.metast.tuoke.module.heal.dal.dataobject.archivesuser.ArchivesUserDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;

import cn.metast.tuoke.module.heal.dal.mysql.archivesuser.ArchivesUserMapper;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.module.heal.enums.ErrorCodeConstants.ARCHIVES_USER_NOT_EXISTS;

/**
 * 档案信息关联用户 Service 实现类
 *
 * @author 超级管理员
 */
@Service
@Validated
public class ArchivesUserServiceImpl implements ArchivesUserService {

    @Resource
    private ArchivesUserMapper archivesUserMapper;

    @Override
    public Long createArchivesUser(ArchivesUserSaveReqVO createReqVO) {
        // 插入
        ArchivesUserDO archivesUser = BeanUtils.toBean(createReqVO, ArchivesUserDO.class);
        archivesUserMapper.insert(archivesUser);
        // 返回
        return archivesUser.getId();
    }

    @Override
    public void updateArchivesUser(ArchivesUserSaveReqVO updateReqVO) {
        // 校验存在
        validateArchivesUserExists(updateReqVO.getId());
        // 更新
        ArchivesUserDO updateObj = BeanUtils.toBean(updateReqVO, ArchivesUserDO.class);
        archivesUserMapper.updateById(updateObj);
    }

    @Override
    public void deleteArchivesUser(Long id) {
        // 校验存在
        validateArchivesUserExists(id);
        // 删除
        archivesUserMapper.deleteById(id);
    }

    private void validateArchivesUserExists(Long id) {
        if (archivesUserMapper.selectById(id) == null) {
            throw exception(ARCHIVES_USER_NOT_EXISTS);
        }
    }

    @Override
    public ArchivesUserDO getArchivesUser(Long id) {
        return archivesUserMapper.selectById(id);
    }

    @Override
    public PageResult<ArchivesUserDO> getArchivesUserPage(ArchivesUserPageReqVO pageReqVO) {
        return archivesUserMapper.selectPage(pageReqVO);
    }

}
