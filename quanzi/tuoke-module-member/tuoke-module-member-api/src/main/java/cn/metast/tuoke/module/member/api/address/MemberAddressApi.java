package cn.metast.tuoke.module.member.api.address;

import cn.metast.tuoke.module.member.api.address.dto.MemberAddressRespDTO;
import com.alibaba.fastjson.JSONArray;

import java.util.List;

/**
 * 用户收件地址 API 接口
 *
 * @author metast.cn
 */
public interface MemberAddressApi {

    /**
     * 获得用户收件地址
     *
     * @param id 收件地址编号
     * @param userId 用户编号
     * @return 用户收件地址
     */
    MemberAddressRespDTO getAddress(Long id, Long userId);

    /**
     * 获得用户默认收件地址
     *
     * @param userId 用户编号
     * @return 用户收件地址
     */
    MemberAddressRespDTO getDefaultAddress(Long userId);


    int saveAddress(String userName, String phoneNum, Long areaId, String address, int isDefault, Long userId);

    int updateShopAddress_live(Long id, String userName, String phoneNum, Long areaId, String address, int isDefault, Long userId);

    JSONArray getShopAddrList(Long userId);

    int delAddress(Long id, Long userId);

}
