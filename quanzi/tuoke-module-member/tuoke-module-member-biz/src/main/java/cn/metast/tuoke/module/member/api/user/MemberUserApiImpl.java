package cn.metast.tuoke.module.member.api.user;

import cn.metast.tuoke.module.member.api.user.dto.MemberUserRespDTO;
import cn.metast.tuoke.module.member.controller.app.user.vo.AppMemberUserUpdateReqVO;
import cn.metast.tuoke.module.member.convert.user.MemberUserConvert;
import cn.metast.tuoke.module.member.dal.dataobject.user.MemberUserDO;
import cn.metast.tuoke.module.member.service.user.MemberUserService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.annotation.Resource;
import java.util.Collection;
import java.util.List;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.module.member.enums.ErrorCodeConstants.USER_MOBILE_NOT_EXISTS;

/**
 * 会员用户的 API 实现类
 *
 * @author metast.cn
 */
@Service
@Validated
public class MemberUserApiImpl implements MemberUserApi {

    @Resource
    private MemberUserService userService;

    @Override
    public MemberUserRespDTO getUser(Long id) {
        MemberUserDO user = userService.getUser(id);
        return MemberUserConvert.INSTANCE.convert2(user);
    }
    @Override
    public MemberUserRespDTO getUserByAnchorId(Long anchorId) {
        MemberUserDO user = userService.getUserByAnchorId(anchorId);
        return MemberUserConvert.INSTANCE.convert2(user);
    }

    @Override
    public void updateUserName(Long id, String name) {
        userService.updateUserName(id, name);
    }

    @Override
    public List<MemberUserRespDTO> getUserList(Collection<Long> ids) {
        return MemberUserConvert.INSTANCE.convertList2(userService.getUserList(ids));
    }

    @Override
    public List<MemberUserRespDTO> getUserListByNickname(String nickname) {
        return MemberUserConvert.INSTANCE.convertList2(userService.getUserListByNickname(nickname));
    }

    @Override
    public MemberUserRespDTO getUserByMobile(String mobile) {
        return MemberUserConvert.INSTANCE.convert2(userService.getUserByMobile(mobile));
    }
    @Override
    public MemberUserRespDTO getUserByNickName(String nickname) {
        return MemberUserConvert.INSTANCE.convert2(userService.getUserByNickName(nickname));
    }

    @Override
    public MemberUserRespDTO selectByTechUserId(String userId) {
        return MemberUserConvert.INSTANCE.convert2(userService.selectByTechUserId(userId));
    }

    @Override
    public void validateUser(Long id) {
        MemberUserDO user = userService.getUser(id);
        if (user == null) {
            throw exception(USER_MOBILE_NOT_EXISTS);
        }
    }

    @Override
    public Boolean updateUserMark(Long userId, String introduction) {
        userService.updateUser(userId, new AppMemberUserUpdateReqVO().setMark(introduction));
        return null;
    }

}
