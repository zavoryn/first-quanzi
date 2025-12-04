package cn.metast.tuoke.module.heal.service.archives;

import cn.metast.tuoke.module.member.api.user.MemberUserApi;
import cn.metast.tuoke.module.member.api.user.dto.MemberUserRespDTO;
import cn.metast.tuoke.module.heal.dal.dataobject.device.DeviceDO;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import cn.metast.tuoke.module.heal.controller.admin.archives.vo.*;
import cn.metast.tuoke.module.heal.dal.dataobject.archives.ArchivesDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;

import cn.metast.tuoke.module.heal.dal.mysql.archives.ArchivesMapper;

import java.util.List;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.module.heal.enums.ErrorCodeConstants.ARCHIVES_NOT_EXISTS;

/**
 * 档案信息 Service 实现类
 *
 * @author 超级管理员
 */
@Service
@Validated
public class ArchivesServiceImpl implements ArchivesService {

    @Resource
    private ArchivesMapper archivesMapper;
    @Resource
    private MemberUserApi memberUserApi;

    @Override
    public Long createArchives(ArchivesSaveReqVO createReqVO) {
        // 插入
        ArchivesDO archives = BeanUtils.toBean(createReqVO, ArchivesDO.class);
        archivesMapper.insert(archives);
        // 返回
        return archives.getId();
    }

    @Override
    public void updateArchives(ArchivesSaveReqVO updateReqVO) {
        // 校验存在
        validateArchivesExists(updateReqVO.getId());
        // 更新
        ArchivesDO updateObj = BeanUtils.toBean(updateReqVO, ArchivesDO.class);
        archivesMapper.updateById(updateObj);
    }

    @Override
    public void deleteArchives(Long id) {
        // 校验存在
        validateArchivesExists(id);
        // 删除
        archivesMapper.deleteById(id);
    }

    private void validateArchivesExists(Long id) {
        if (archivesMapper.selectById(id) == null) {
            throw exception(ARCHIVES_NOT_EXISTS);
        }
    }

    @Override
    public ArchivesDO getArchives(Long id) {
        return archivesMapper.selectById(id);
    }

    @Override
    public PageResult<ArchivesDO> getArchivesPage(ArchivesPageReqVO pageReqVO) {
        PageResult<ArchivesDO> result = archivesMapper.selectPage(pageReqVO);
        List<ArchivesDO> list = result.getList();
        if(list.size() > 0){
            for(ArchivesDO item : list){
                if(item.getUid() != null){
                    MemberUserRespDTO user = memberUserApi.getUser(item.getUid());
                    if(user != null){
                        item.setUname(user.getNickname());
                    }
                }
            }
            result.setList(list);
        }
        return result;
    }

}
