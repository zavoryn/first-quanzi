package cn.metast.tuoke.module.kaifa.controller.admin.operatelog;
import cn.metast.tuoke.framework.common.pojo.CommonResult;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;
import cn.metast.tuoke.module.kaifa.common.KaifaBizTypeEnum;
import cn.metast.tuoke.module.kaifa.enums.LogRecordConstants;
import cn.metast.tuoke.module.kaifa.controller.admin.operatelog.vo.KaifaOperateLogPageReqVO;
import cn.metast.tuoke.module.kaifa.controller.admin.operatelog.vo.KaifaOperateLogRespVO;
import cn.metast.tuoke.module.system.api.logger.OperateLogApi;
import cn.metast.tuoke.module.system.api.logger.dto.OperateLogPageReqDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static cn.metast.tuoke.framework.common.pojo.CommonResult.success;
import static cn.metast.tuoke.framework.common.pojo.PageParam.PAGE_SIZE_NONE;
import static cn.metast.tuoke.module.kaifa.enums.LogRecordConstants.KAIFA_TYPE;

@Tag(name = "管理后台 - CRM 操作日志")
@RestController
@RequestMapping("/kaifa/operate-log")
@Validated
public class KaifaOperateLogController {
    @Resource
    private OperateLogApi operateLogApi;

    /**
     * {@link KaifaBizTypeEnum} 与 {@link LogRecordConstants} 的映射关系
     */
    private static final Map<Integer, String> BIZ_TYPE_MAP = new HashMap<>();

    static {
        BIZ_TYPE_MAP.put(KaifaBizTypeEnum.SHE_MEI.getType(), KAIFA_TYPE);
    }

    @GetMapping("/page")
    @Operation(summary = "获得操作日志")
    public CommonResult<PageResult<KaifaOperateLogRespVO>> getCustomerOperateLog(@Valid KaifaOperateLogPageReqVO pageReqVO) {
        OperateLogPageReqDTO reqDTO = new OperateLogPageReqDTO();
        reqDTO.setPageSize(PAGE_SIZE_NONE); // 默认不分页，需要分页需注释
        reqDTO.setType(BIZ_TYPE_MAP.get(pageReqVO.getBizType())).setBizId(pageReqVO.getBizId());
        return success(BeanUtils.toBean(operateLogApi.getOperateLogPage(reqDTO), KaifaOperateLogRespVO.class));
    }

}
