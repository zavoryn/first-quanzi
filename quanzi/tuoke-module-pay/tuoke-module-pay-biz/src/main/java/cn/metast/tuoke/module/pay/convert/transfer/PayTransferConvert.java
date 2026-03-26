package cn.metast.tuoke.module.pay.convert.transfer;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.pay.core.client.dto.transfer.PayTransferUnifiedReqDTO;
import cn.metast.tuoke.module.pay.api.transfer.dto.PayTransferCreateReqDTO;
import cn.metast.tuoke.module.pay.controller.admin.demo.vo.transfer.PayDemoTransferCreateReqVO;
import cn.metast.tuoke.module.pay.controller.admin.transfer.vo.PayTransferCreateReqVO;
import cn.metast.tuoke.module.pay.controller.admin.transfer.vo.PayTransferPageItemRespVO;
import cn.metast.tuoke.module.pay.controller.admin.transfer.vo.PayTransferRespVO;
import cn.metast.tuoke.module.pay.dal.dataobject.transfer.PayTransferDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PayTransferConvert {

    PayTransferConvert INSTANCE = Mappers.getMapper(PayTransferConvert.class);

    PayTransferDO convert(PayTransferCreateReqDTO dto);

    PayTransferUnifiedReqDTO convert2(PayTransferDO dto);

    PayTransferCreateReqDTO convert(PayTransferCreateReqVO vo);

    PayTransferCreateReqDTO convert(PayDemoTransferCreateReqVO vo);

    PayTransferRespVO convert(PayTransferDO bean);

    PageResult<PayTransferPageItemRespVO> convertPage(PageResult<PayTransferDO> pageResult);

}
