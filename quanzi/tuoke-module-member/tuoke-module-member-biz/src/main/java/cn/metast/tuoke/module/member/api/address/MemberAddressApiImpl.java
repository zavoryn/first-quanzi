package cn.metast.tuoke.module.member.api.address;

import cn.metast.tuoke.framework.common.util.object.BeanUtils;
import cn.metast.tuoke.framework.ip.core.Area;
import cn.metast.tuoke.framework.ip.core.utils.AreaUtils;
import cn.metast.tuoke.module.member.api.address.dto.MemberAddressRespDTO;
import cn.metast.tuoke.module.member.controller.app.address.vo.AppAddressCreateReqVO;
import cn.metast.tuoke.module.member.controller.app.address.vo.AppAddressUpdateReqVO;
import cn.metast.tuoke.module.member.convert.address.AddressConvert;
import cn.metast.tuoke.module.member.dal.dataobject.address.MemberAddressDO;
import cn.metast.tuoke.module.member.service.address.AddressService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.annotation.Resource;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 用户收件地址 API 实现类
 *
 * @author metast.cn
 */
@Service
@Validated
public class MemberAddressApiImpl implements MemberAddressApi {

    @Resource
    private AddressService addressService;

    @Override
    public MemberAddressRespDTO getAddress(Long id, Long userId) {
        return AddressConvert.INSTANCE.convert02(addressService.getAddress(userId, id));
    }

    @Override
    public MemberAddressRespDTO getDefaultAddress(Long userId) {
        return AddressConvert.INSTANCE.convert02(addressService.getDefaultUserAddress(userId));
    }

    @Override
    public int saveAddress(String userName, String phoneNum, Long areaId, String address, int isDefault, Long userId){
        AppAddressCreateReqVO addressCreateReqVO = new AppAddressCreateReqVO();
        addressCreateReqVO.setName(userName);
        addressCreateReqVO.setMobile(phoneNum);
        addressCreateReqVO.setAreaId(areaId);
        addressCreateReqVO.setDetailAddress(address);
        addressCreateReqVO.setDefaultStatus(isDefault > 0);
        Long addId = addressService.createAddress(userId, addressCreateReqVO);
        return addId != null ? 1 : 0;
    }

    @Override
    public int updateShopAddress_live(Long id, String userName, String phoneNum, Long areaId, String address, int isDefault, Long userId){
        AppAddressUpdateReqVO updateReqVO = new AppAddressUpdateReqVO();
        updateReqVO.setId(id);
        updateReqVO.setName(userName);
        updateReqVO.setMobile(phoneNum);
        updateReqVO.setAreaId(areaId);
        updateReqVO.setDetailAddress(address);
        updateReqVO.setDefaultStatus(isDefault > 0);
        int i = addressService.updateAddress_live(userId, updateReqVO);
        return i;
    }

    @Override
    public JSONArray getShopAddrList(Long userId) {
        List<MemberAddressDO> addressList = addressService.getAddressList(userId);
//        List<MemberAddressRespDTO> memberAddressRespDTOS = BeanUtils.toBean(addressList, MemberAddressRespDTO.class);

        JSONArray list = new JSONArray();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for(MemberAddressDO dto : addressList){

            Area area = AreaUtils.getArea(dto.getAreaId().intValue());
            JSONObject item = new JSONObject();
            item.put("id", dto.getId());
            item.put("uid", dto.getUserId());
            item.put("userName", dto.getName());
            item.put("phoneNum", dto.getMobile());
            item.put("areaId", dto.getAreaId());
            item.put("address", dto.getDetailAddress());
            item.put("isDefault", dto.getDefaultStatus() != null && dto.getDefaultStatus() ? 1 : 0);

            if(area != null){
                item.put("area", area.getName());
                item.put("city", area.getParent().getName());
                Area area2 = AreaUtils.getArea(area.getParent().getId());
                if(area2 != null){
                    item.put("pro", area2.getParent().getName());
                }
            }

            String addTime = "";
            if(dto.getCreateTime() != null){
                addTime = dto.getCreateTime().format(formatter);
            }
            item.put("addTime", addTime);
            list.add(item);
        }
        return list;
    }

    @Override
    public int delAddress(Long id, Long userId){
        return addressService.deleteAddress_live(userId, id);
    }

}
