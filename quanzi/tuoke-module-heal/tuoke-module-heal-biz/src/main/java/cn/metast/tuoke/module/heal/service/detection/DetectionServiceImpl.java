package cn.metast.tuoke.module.heal.service.detection;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.metast.tuoke.framework.common.exception.ServiceException;
import cn.metast.tuoke.framework.common.util.http.HttpUtils;
import cn.metast.tuoke.module.member.api.user.MemberUserApi;
import cn.metast.tuoke.module.member.api.user.dto.MemberUserRespDTO;
import cn.metast.tuoke.module.heal.dal.dataobject.archives.ArchivesDO;
import cn.metast.tuoke.module.heal.service.archives.ArchivesService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.metast.tuoke.module.heal.controller.admin.detection.vo.*;
import cn.metast.tuoke.module.heal.dal.dataobject.detection.DetectionDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;

import cn.metast.tuoke.module.heal.dal.mysql.detection.DetectionMapper;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.module.heal.enums.ErrorCodeConstants.DETECTION_NOT_EXISTS;

/**
 * 检测记录 Service 实现类
 *
 * @author 超级管理员
 */
@Service
@Validated
public class DetectionServiceImpl implements DetectionService {

    @Resource
    private DetectionMapper detectionMapper;
    @Resource
    private MemberUserApi memberUserApi;
    @Resource
    private ArchivesService archivesService;

    @Override
    public Long createDetection(DetectionSaveReqVO createReqVO) {
        // 插入
        DetectionDO detection = BeanUtils.toBean(createReqVO, DetectionDO.class);
        if (StringUtils.isNotBlank(createReqVO.getName()))
        {
            createReqVO.setName(createReqVO.getName().trim());
        }
        detectionMapper.insert(detection);
        // 返回
        return detection.getId();
    }

    @Override
    public void updateDetection(DetectionSaveReqVO updateReqVO) {
        // 校验存在
        validateDetectionExists(updateReqVO.getId());
        // 更新
        DetectionDO updateObj = BeanUtils.toBean(updateReqVO, DetectionDO.class);
        if (StringUtils.isNotBlank(updateReqVO.getName()))
        {
            updateReqVO.setName(updateReqVO.getName().trim());
        }
        detectionMapper.updateById(updateObj);
    }

    @Override
    public void deleteDetection(Long id) {
        // 校验存在
        validateDetectionExists(id);
        // 删除
        detectionMapper.deleteById(id);
    }

    private void validateDetectionExists(Long id) {
        if (detectionMapper.selectById(id) == null) {
            throw exception(DETECTION_NOT_EXISTS);
        }
    }

    @Override
    public DetectionDO getDetection(Long id) {
        return detectionMapper.selectById(id);
    }

    @Override
    public DetectionDO getDetectionUid(Long uId) {
        return detectionMapper.selectByUid(uId);
    }

    @Override
    public PageResult<DetectionDO> getDetectionPage(DetectionPageReqVO pageReqVO) {
        PageResult<DetectionDO> result = detectionMapper.selectPage(pageReqVO);
        List<DetectionDO> list = result.getList();
        if(list.size() > 0){
            for(DetectionDO item : list){
                if(item.getUid() != null){
                    MemberUserRespDTO user = memberUserApi.getUser(item.getUid());
                    if(user != null){
                        item.setUname(user.getNickname());
                    }
                }
                if(item.getAid() != null){
                    ArchivesDO archives = archivesService.getArchives(item.getAid());
                    if(archives != null){
                        item.setAname(archives.getName());
                    }
                }
            }
            result.setList(list);
        }
        return result;
    }

    @Override
    public List<DetectionDO> getExcelList(DetectionRespVO respVO){
        return detectionMapper.getExcelList(respVO);
    }

    @Override
    public Map<String, Object> getReportData(DetectionDO item){
        Map<String, Object> info = new HashMap<>();
        info.put("code", 200);

        String report = (StringUtils.isNotBlank(item.getReport()) ? item.getReport().trim():"");
        try {
            Double aDouble = Double.valueOf(report);
            if(aDouble < 2){
                report = "2";
            }else if(aDouble > 14){
                report = "14";
            }
        }catch (Exception e){
            throw new ServiceException(500, "报告生成失败");
        }

        try {
            String url = "https://ihipro.net/nologin/api/report/getReportForOpen";
            url = url + "?projectName=" + (StringUtils.isNotBlank(item.getName()) ? item.getName().trim():"HbA1c") +
                    "&testResult=" + report +
                    "&testTime=" + (item.getCreateTime() != null ? LocalDateTimeUtil.format(item.getCreateTime(), DatePattern.NORM_DATETIME_PATTERN):LocalDateTimeUtil.format(LocalDateTimeUtil.now(), DatePattern.NORM_DATETIME_PATTERN));
            Map<String, String> header = new HashMap<>();
            header.put("Content-Type", "application/json");
            String responseBody = HttpUtils.get(url, header);
            JSONObject resObj = JSONUtil.parseObj(responseBody);
            if(resObj.getInt("code") == 0){
                JSONObject data = resObj.getJSONObject("data");
                if(data != null){
                    JSONArray list = data.getJSONArray("list");
                    if(list != null && list.size() > 0){
                        JSONArray children = list.getJSONObject(0).getJSONArray("children");
                        if(children != null && children.size() > 0){
                            info.put("data", children.getJSONObject(0));
                        }
                    }
                }
            }else{
                info.put("code", 500);
                info.put("msg", resObj.getStr("msg"));
            }
        }catch (Exception e){
            throw new ServiceException(500, "网络异常, 请重新尝试");
        }
        return info;
    }

}
