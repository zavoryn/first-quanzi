package cn.metast.tuoke.module.community.api.cmTopic;

import cn.metast.tuoke.module.community.api.cmTopic.dto.TopicMemberRespDTO;
import cn.metast.tuoke.module.community.dal.dataobject.cmTopic.CmTopicDO;
import cn.metast.tuoke.module.community.dal.dataobject.cmTopicmember.CmTopicMemberDO;
import cn.metast.tuoke.module.community.dal.mysql.cmTopic.CmTopicMapper;
import cn.metast.tuoke.module.community.dal.mysql.cmTopicmember.CmTopicMemberMapper;
import cn.metast.tuoke.module.community.service.cmTopic.CmTopicService;
import cn.metast.tuoke.module.member.api.user.MemberUserApi;
import cn.metast.tuoke.module.member.api.user.dto.MemberUserRespDTO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 会员用户的 API 实现类
 *
 * @author metast.cn
 */
@Service
@Validated
public class TopicApiImpl implements TopicApi {
    @Resource
    private CmTopicService cmTopicService;
    @Resource
    private CmTopicMemberMapper cmTopicMemberMapper;
    @Resource
    private CmTopicMapper cmTopicMapper;

    @Override
    public Long getTopicUserId(Long topicId) {
        CmTopicDO cmTopic = cmTopicService.getCmTopic(topicId);
        return cmTopic.getUserId();
    }

    @Override
    public List<TopicMemberRespDTO> getTopicMemberListByUserIds(List<Long> userIds) {
        if (userIds == null || userIds.isEmpty()) {
            return new ArrayList<>();
        }
        // 查询圈子成员信息
        List<CmTopicMemberDO> memberList = cmTopicMemberMapper.selectList(
                new LambdaQueryWrapper<CmTopicMemberDO>()
                        .in(CmTopicMemberDO::getUserId, userIds)
                        .orderByDesc(CmTopicMemberDO::getCreateTime)
        );
        if (memberList.isEmpty()) {
            return new ArrayList<>();
        }
        // 获取圈子ID集合
        List<Long> topicIds = memberList.stream()
                .map(CmTopicMemberDO::getTopicId)
                .distinct()
                .collect(Collectors.toList());
        // 批量查询圈子信息
        List<CmTopicDO> topicList = cmTopicMapper.selectBatchIds(topicIds);
        Map<Long, String> topicNameMap = topicList.stream()
                .collect(Collectors.toMap(CmTopicDO::getId, CmTopicDO::getTopicName, (v1, v2) -> v1));
        // 组装返回结果
        return memberList.stream().map(member -> {
            TopicMemberRespDTO dto = new TopicMemberRespDTO();
            dto.setId(member.getId());
            dto.setUserId(member.getUserId());
            dto.setTopicId(member.getTopicId());
            dto.setTopicName(topicNameMap.get(member.getTopicId()));
            dto.setStartTime(member.getStartTime());
            dto.setEndTime(member.getEndTime());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public Boolean isRenewalCustomer(Long userId, Long topicId, Long currentMemberId) {
        // 查询同一用户在同一圈子中，ID小于当前记录ID的历史记录数量
        Long count = cmTopicMemberMapper.selectCount(
                new LambdaQueryWrapper<CmTopicMemberDO>()
                        .eq(CmTopicMemberDO::getUserId, userId)
                        .eq(CmTopicMemberDO::getTopicId, topicId)
                        .lt(CmTopicMemberDO::getId, currentMemberId)
        );
        return count > 0;
    }

    @Override
    public Boolean isTopicCreator(Long userId, Long topicId) {
        if (userId == null || topicId == null) {
            return false;
        }
        Long count = cmTopicMemberMapper.selectCount(
                new LambdaQueryWrapper<CmTopicMemberDO>()
                        .eq(CmTopicMemberDO::getUserId, userId)
                        .eq(CmTopicMemberDO::getTopicId, topicId)
                        .eq(CmTopicMemberDO::getRole, 1)
        );
        return count > 0;
    }
}
