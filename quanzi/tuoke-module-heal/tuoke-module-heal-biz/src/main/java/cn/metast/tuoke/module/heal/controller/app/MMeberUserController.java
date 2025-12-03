package cn.metast.tuoke.module.heal.controller.app;

import cn.metast.tuoke.framework.common.pojo.CommonResult;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.util.date.DateUtils;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;
import cn.metast.tuoke.framework.security.core.util.SecurityFrameworkUtils;
import cn.metast.tuoke.module.heal.controller.admin.healService.vo.HealServicePageReqVO;
import cn.metast.tuoke.module.heal.controller.admin.healService.vo.HealServiceRespVO;
import cn.metast.tuoke.module.heal.dal.dataobject.detection.DetectionDO;
import cn.metast.tuoke.module.heal.dal.dataobject.healService.HealServiceDO;
import cn.metast.tuoke.module.heal.service.detection.DetectionService;
import cn.metast.tuoke.module.heal.service.healService.HealServiceService;
import cn.metast.tuoke.module.member.api.user.MemberUserApi;
import cn.metast.tuoke.module.member.api.user.dto.MemberUserRespDTO;
import cn.metast.tuoke.module.system.api.dict.DictDataApi;
import cn.metast.tuoke.module.system.api.dict.dto.DictDataRespDTO;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static cn.metast.tuoke.framework.common.pojo.CommonResult.success;

@Api("体检用户")
@RestController
@RequestMapping("/api/meberUser")
public class MMeberUserController {
    @Resource
    private MemberUserApi memberUserApi;
    @Resource
    private DictDataApi dictDataApi;
    @Resource
    private DetectionService detectionService;
    @GetMapping("/getMeberUser")
    @Operation(summary = "体检用户")
    @PermitAll
    public CommonResult<MemberUserRespDTO> getServicePage() {
        Long userId= SecurityFrameworkUtils.getLoginUserId()!=null?SecurityFrameworkUtils.getLoginUserId():286L;
        MemberUserRespDTO memberUserRespDTO=memberUserApi.getUser(userId);
        if(memberUserRespDTO!=null) {
            memberUserRespDTO.setBsNum("8,562");
            memberUserRespDTO.setXlNum("72");
            memberUserRespDTO.setSmNum("8.5");
            //最新体检时间
            DetectionDO detection = detectionService.getDetection(userId);
            if(detection!=null){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                memberUserRespDTO.setTjDate(detection.getCreateTime().format(formatter));
            }
        }
        return success(memberUserRespDTO);
    }
    //0 显示 1不显示
    @GetMapping("/getLiveStatus")
    @Operation(summary = "直播状态")
    @PermitAll
    public CommonResult<String> getLiveStatus(@Valid HealServicePageReqVO pageReqVO) {
        DictDataRespDTO dictDataRespDTO=dictDataApi.parseDictData("live_merchant","live_status");
        if(dictDataRespDTO!=null){
            return success(dictDataRespDTO.getValue());
        }
        return success("0");
    }
}
