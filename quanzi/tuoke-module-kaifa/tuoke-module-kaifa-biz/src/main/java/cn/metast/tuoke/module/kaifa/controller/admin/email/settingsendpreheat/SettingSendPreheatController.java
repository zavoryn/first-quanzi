package cn.metast.tuoke.module.kaifa.controller.admin.email.settingsendpreheat;

import cn.hutool.core.bean.BeanUtil;
import cn.metast.tuoke.framework.tenant.core.context.TenantContextHolder;
import cn.metast.tuoke.module.kaifa.controller.admin.email.settingemail.vo.SettingEmailRespVO;
import cn.metast.tuoke.module.kaifa.service.email.settingemail.SettingEmailService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.*;
import jakarta.servlet.http.*;
import java.util.*;
import java.io.IOException;

import cn.metast.tuoke.framework.common.pojo.PageParam;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.CommonResult;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;

import static cn.metast.tuoke.framework.common.pojo.CommonResult.error;
import static cn.metast.tuoke.framework.common.pojo.CommonResult.success;

import cn.metast.tuoke.framework.excel.core.util.ExcelUtils;

import cn.metast.tuoke.framework.apilog.core.annotation.ApiAccessLog;
import static cn.metast.tuoke.framework.apilog.core.enums.OperateTypeEnum.*;

import cn.metast.tuoke.module.kaifa.controller.admin.email.settingsendpreheat.vo.*;
import cn.metast.tuoke.module.kaifa.dal.dataobject.email.settingsendpreheat.SettingSendPreheatDO;
import cn.metast.tuoke.module.kaifa.service.email.settingsendpreheat.SettingSendPreheatService;

@Tag(name = "管理后台 - 邮箱预热")
@RestController
@RequestMapping("/kaifa/setting-send-preheat")
@Validated
public class SettingSendPreheatController {

    @Resource
    private SettingSendPreheatService settingSendPreheatService;
    @Resource
    private SettingEmailService settingEmailService;
    @PostMapping("/create")
    @Operation(summary = "创建邮箱预热")
    @PreAuthorize("@ss.hasPermission('kaifa:setting-send-preheat:create')")
    public CommonResult<Long> createSettingSendPreheat(@Valid @RequestBody SettingSendPreheatSaveReqVO createReqVO) {
        //创建之前要先检测收发信
        SettingEmailRespVO dto = new SettingEmailRespVO();
        BeanUtils.copyProperties(createReqVO,dto);
        JSONObject checkFlag = settingEmailService.checkEmailStatus(dto);
        Integer code = checkFlag.getInteger("code");
        if(0 == code){
            Long insert = settingSendPreheatService.createSettingSendPreheat(createReqVO);
            //当为发件邮箱时，添加到预热线程中
            if(insert > 0){
                if("2".equals(createReqVO.getType()) &&  "0".equals(createReqVO.getStatus()) && "running".equals(createReqVO.getPreheat())) {
                    //未处理
                    List<SettingEmailRespVO> perheatList = new ArrayList<>();
                    perheatList.add(BeanUtil.copyProperties(createReqVO, SettingEmailRespVO.class));
                    settingSendPreheatService.emailPreheat(perheatList, TenantContextHolder.getRequiredTenantId());
                }
            }
            return success(insert);
        }
        return success(0L);
    }

    @PutMapping("/update")
    @Operation(summary = "更新邮箱预热")
    @PreAuthorize("@ss.hasPermission('kaifa:setting-send-preheat:update')")
    public CommonResult<Boolean> updateSettingSendPreheat(@Valid @RequestBody SettingSendPreheatSaveReqVO updateReqVO) {
        //创建之前要先检测收发信
        SettingEmailRespVO dto = new SettingEmailRespVO();
        BeanUtils.copyProperties(updateReqVO,dto);
        JSONObject checkFlag = settingEmailService.checkEmailStatus(dto);
        Integer code = checkFlag.getInteger("code");
        if(0 == code){
            settingSendPreheatService.updateSettingSendPreheat(updateReqVO);
            //当为发件邮箱时，添加到预热线程中
            if("2".equals(updateReqVO.getType()) &&  "0".equals(updateReqVO.getStatus()) && "running".equals(updateReqVO.getPreheat())) {
                List<SettingEmailRespVO> perheatList = new ArrayList<>();
                perheatList.add(BeanUtil.copyProperties(updateReqVO, SettingEmailRespVO.class));
                settingSendPreheatService.emailPreheat(perheatList, TenantContextHolder.getRequiredTenantId());
            }
            return success(true);
        }
        return success(false);
    }
    /**
     * 邮箱预热首页总数
     */
    @GetMapping(value = "/statPreheat")
    public CommonResult<Map<String, Object>> statPreheat() {
        return success(settingSendPreheatService.statPreheat());
    }
    /**
     * 邮箱预热修改状态
     */
    @PostMapping(value = "/status")
    public CommonResult<Boolean> editStatus(@RequestBody SettingSendPreheatSaveReqVO settingSendPreheat) {
        if("running".equals(settingSendPreheat.getPreheat())){
            SettingEmailRespVO dto = new SettingEmailRespVO();
            BeanUtils.copyProperties(settingSendPreheat,dto);
            JSONObject checkFlag = settingEmailService.checkEmailStatus(dto);
            Integer code = checkFlag.getInteger("code");
            if(0 == code){
                settingSendPreheatService.updateSettingSendPreheat(settingSendPreheat);
                //当为发件邮箱时，添加到预热线程中
                if("2".equals(settingSendPreheat.getType()) &&  "0".equals(settingSendPreheat.getStatus()) && "running".equals(settingSendPreheat.getPreheat())) {
                    List<SettingEmailRespVO> perheatList = new ArrayList<>();
                    perheatList.add(BeanUtil.copyProperties(settingSendPreheat, SettingEmailRespVO.class));
                    settingSendPreheatService.emailPreheat(perheatList,TenantContextHolder.getRequiredTenantId());
                }
                return success(true);
            }
        }else{
            settingSendPreheatService.cancelTask(settingSendPreheat.getEmail());
        }
        settingSendPreheatService.updateSettingSendPreheat(settingSendPreheat);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除邮箱预热")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('kaifa:setting-send-preheat:delete')")
    public CommonResult<Boolean> deleteSettingSendPreheat(@RequestParam("id") Long id) {
        settingSendPreheatService.deleteSettingSendPreheat(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得邮箱预热")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('kaifa:setting-send-preheat:query')")
    public CommonResult<SettingSendPreheatRespVO> getSettingSendPreheat(@RequestParam("id") Long id) {
        SettingSendPreheatDO settingSendPreheat = settingSendPreheatService.getSettingSendPreheat(id);
        return success(BeanUtils.toBean(settingSendPreheat, SettingSendPreheatRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得邮箱预热分页")
    @PreAuthorize("@ss.hasPermission('kaifa:setting-send-preheat:query')")
    public CommonResult<PageResult<SettingSendPreheatRespVO>> getSettingSendPreheatPage(@Valid SettingSendPreheatPageReqVO pageReqVO) {
        if(!StringUtils.isEmpty(pageReqVO.getHost())){
            pageReqVO.setHost(null);
        }
        PageResult<SettingSendPreheatDO> pageResult = settingSendPreheatService.getSettingSendPreheatPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SettingSendPreheatRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出邮箱预热 Excel")
    @PreAuthorize("@ss.hasPermission('kaifa:setting-send-preheat:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSettingSendPreheatExcel(@Valid SettingSendPreheatPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SettingSendPreheatDO> list = settingSendPreheatService.getSettingSendPreheatPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "邮箱预热.xls", "数据", SettingSendPreheatRespVO.class,
                        BeanUtils.toBean(list, SettingSendPreheatRespVO.class));
    }

}
