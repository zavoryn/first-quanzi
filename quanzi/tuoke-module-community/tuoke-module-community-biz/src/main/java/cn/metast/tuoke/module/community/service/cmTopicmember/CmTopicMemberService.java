package cn.metast.tuoke.module.community.service.cmTopicmember;

import java.util.*;
import jakarta.validation.*;
import cn.metast.tuoke.module.community.controller.admin.cmTopicmember.vo.*;
import cn.metast.tuoke.module.community.dal.dataobject.cmTopicmember.CmTopicMemberDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import java.util.List;

/**
 * 圈子成员 Service 接口
 *
 * @author 苏丹家园
 */
public interface CmTopicMemberService {

    /**
     * 创建圈子成员
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCmTopicMember(@Valid CmTopicMemberSaveReqVO createReqVO);

    /**
     * 更新圈子成员
     *
     * @param updateReqVO 更新信息
     */
    void updateCmTopicMember(@Valid CmTopicMemberSaveReqVO updateReqVO);

    /**
     * 删除圈子成员
     *
     * @param id 编号
     */
    void deleteCmTopicMember(Long id);

    /**
     * 获得圈子成员
     *
     * @param id 编号
     * @return 圈子成员
     */
    CmTopicMemberDO getCmTopicMember(Long id);

    /**
     * 获得圈子成员分页
     *
     * @param pageReqVO 分页查询
     * @return 圈子成员分页
     */
    PageResult<CmTopicMemberDO> getCmTopicMemberPage(CmTopicMemberPageReqVO pageReqVO);

    /**
     * 会员列表详情
     *
     * @param pageReqVO 分页查询
     * @return 圈子成员分页
     */
    PageResult<CmTopicMemberDO> getCmTopicMemberList(CmTopicMemberPageReqVO pageReqVO);

    /**
     * 会员列表包含群主自己
     *
     * @param pageReqVO 分页查询
     * @return 圈子成员分页
     */
    PageResult<CmTopicMemberDO> getCmTopicMemberOwnList(CmTopicMemberPageReqVO pageReqVO);
    /**
     * 收入明细
     *
     * @return 圈子成员列表
     */
    PageResult<CmTopicMemberDO> getIncomeDetailList(CmTopicMemberPageReqVO pageReqVO);
    /*
     * 我的购买
     */
    PageResult<CmTopicMemberDO> getMyShopList(CmTopicMemberPageReqVO pageReqVO);

    /**
     *加入圈子
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long joinCmTopicMember(@Valid CmTopicMemberSaveReqVO createReqVO);

    /**
     * 审核加入圈子的成员
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long checkMember(@Valid CmTopicMemberSaveReqVO createReqVO);

    /**
     * 圈子成员列表
     *
     * @param pageReqVO 分页查询
     * @return 圈子成员列表
     */
    CmTopicMemberDO getCmTopicMember(Long userId,Long topicId);


    /**
     * 私信会员列表
     * @param pageReqVO
     * @return
     */
    PageResult<CmTopicMemberDO> getCmTopicMemberConversationList(CmTopicMemberPageReqVO pageReqVO);

    /**
     * 获取所有圈子简单信息列表（用于下拉选择）
     */
    List<TopicSimpleRespVO> getTopicSimpleList();

    /**
     * 添加免费会员
     *
     * @param reqVO 请求参数
     * @return 成员ID
     */
    Long addFreeMember(@Valid FreeMemberAddReqVO reqVO);

    /**
     * 会员用户注销（逻辑删除该用户的所有会员记录）
     *
     * @param userId 用户ID
     */
    void cancelMemberByUserId(Long userId);
}
