package cn.metast.tuoke.module.system.convert.tenant;

import cn.hutool.core.util.ObjectUtil;
import cn.metast.tuoke.module.system.controller.admin.tenant.vo.tenant.TenantSaveReqVO;
import cn.metast.tuoke.module.system.controller.admin.user.vo.user.UserSaveReqVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 租户 Convert
 *
 * @author metast.cn
 */
@Mapper
public interface TenantConvert {

    TenantConvert INSTANCE = Mappers.getMapper(TenantConvert.class);

    default UserSaveReqVO convert02(TenantSaveReqVO bean) {
        UserSaveReqVO reqVO = new UserSaveReqVO();
        if(ObjectUtil.isNotEmpty(bean.getContactId())){
            reqVO.setId(bean.getContactId());
        }
        reqVO.setUsername(bean.getUsername());
        reqVO.setPassword(bean.getPassword());
        reqVO.setNickname(bean.getContactName()).setMobile(bean.getContactMobile());
        return reqVO;
    }

}
