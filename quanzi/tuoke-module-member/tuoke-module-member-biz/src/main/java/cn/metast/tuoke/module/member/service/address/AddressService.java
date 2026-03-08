package cn.metast.tuoke.module.member.service.address;

import cn.metast.tuoke.module.member.controller.app.address.vo.AppAddressCreateReqVO;
import cn.metast.tuoke.module.member.controller.app.address.vo.AppAddressUpdateReqVO;
import cn.metast.tuoke.module.member.dal.dataobject.address.MemberAddressDO;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 用户收件地址 Service 接口
 *
 * @author metast.cn
 */
public interface AddressService {

    /**
     * 创建用户收件地址
     *
     *
     * @param userId 用户编号
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createAddress(Long userId, @Valid AppAddressCreateReqVO createReqVO);

    /**
     * 更新用户收件地址
     *
     * @param userId 用户编号
     * @param updateReqVO 更新信息
     */
    void updateAddress(Long userId, @Valid AppAddressUpdateReqVO updateReqVO);
    int updateAddress_live(Long userId, @Valid AppAddressUpdateReqVO updateReqVO);

    /**
     * 删除用户收件地址
     *
     * @param userId 用户编号
     * @param id 编号
     */
    void deleteAddress(Long userId, Long id);
    int deleteAddress_live(Long userId, Long id);

    /**
     * 获得用户收件地址
     *
     * @param id 编号
     * @return 用户收件地址
     */
    MemberAddressDO getAddress(Long userId, Long id);

    /**
     * 获得用户收件地址列表
     *
     * @param userId 用户编号
     * @return 用户收件地址列表
     */
    List<MemberAddressDO> getAddressList(Long userId);

    /**
     * 获得用户默认的收件地址
     *
     * @param userId 用户编号
     * @return 用户收件地址
     */
    MemberAddressDO getDefaultUserAddress(Long userId);

}
