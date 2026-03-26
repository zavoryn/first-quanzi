package cn.metast.tuoke.module.pay.convert.demo;

import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.module.pay.controller.admin.demo.vo.order.PayDemoOrderCreateReqVO;
import cn.metast.tuoke.module.pay.controller.admin.demo.vo.order.PayDemoOrderRespVO;
import cn.metast.tuoke.module.pay.dal.dataobject.demo.PayDemoOrderDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 示例订单 Convert
 *
 * @author metast.cn
 */
@Mapper
public interface PayDemoOrderConvert {

    PayDemoOrderConvert INSTANCE = Mappers.getMapper(PayDemoOrderConvert.class);

    PayDemoOrderDO convert(PayDemoOrderCreateReqVO bean);

    PayDemoOrderRespVO convert(PayDemoOrderDO bean);

    PageResult<PayDemoOrderRespVO> convertPage(PageResult<PayDemoOrderDO> page);

}
