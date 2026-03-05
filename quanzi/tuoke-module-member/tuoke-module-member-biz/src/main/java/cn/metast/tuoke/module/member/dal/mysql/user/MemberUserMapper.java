package cn.metast.tuoke.module.member.dal.mysql.user;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.mybatis.core.mapper.BaseMapperX;
import cn.metast.tuoke.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.metast.tuoke.module.member.controller.admin.user.vo.MemberUserPageReqVO;
import cn.metast.tuoke.module.member.dal.dataobject.user.MemberUserDO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 会员 User Mapper
 *
 * @author metast.cn
 */
@Mapper
public interface MemberUserMapper extends BaseMapperX<MemberUserDO> {

    default MemberUserDO selectByMobile(String mobile) {
        return selectOne(MemberUserDO::getMobile, mobile);
    }
    default MemberUserDO selectByTechUserId(String userId) {
        return selectOne(MemberUserDO::getTechUserId, userId);
    }
    default MemberUserDO selectByNickname(String nickName) {
        return selectOne(new LambdaQueryWrapper<>(MemberUserDO.class)
                .eq(MemberUserDO::getNickname, nickName)
                .last("limit 1"));
    }
    default List<MemberUserDO> selectListByNicknameLike(String nickname) {
        return selectList(new LambdaQueryWrapperX<MemberUserDO>()
                .likeIfPresent(MemberUserDO::getNickname, nickname));
    }

    default PageResult<MemberUserDO> selectPage(MemberUserPageReqVO reqVO) {
        // 处理 tagIds 过滤条件
        String tagIdSql = "";
        if (CollUtil.isNotEmpty(reqVO.getTagIds())) {
            tagIdSql = reqVO.getTagIds().stream()
                    .map(tagId -> "FIND_IN_SET(" + tagId + ", tag_ids)")
                    .collect(Collectors.joining(" OR "));
        }
        // 分页查询
        return selectPage(reqVO, new LambdaQueryWrapperX<MemberUserDO>()
                .likeIfPresent(MemberUserDO::getMobile, reqVO.getMobile())
                .betweenIfPresent(MemberUserDO::getLoginDate, reqVO.getLoginDate())
                .likeIfPresent(MemberUserDO::getNickname, reqVO.getNickname())
                .betweenIfPresent(MemberUserDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(MemberUserDO::getLevelId, reqVO.getLevelId())
                .eqIfPresent(MemberUserDO::getGroupId, reqVO.getGroupId())
                .apply(StrUtil.isNotEmpty(tagIdSql), tagIdSql)
                .orderByDesc(MemberUserDO::getId));
    }

    /**
     * 分页查询会员用户，支持部门数据隔离
     *
     * @param reqVO 查询参数
     * @param orderIds 允许查询的订单ID列表
     * @return 分页结果
     */
    default PageResult<MemberUserDO> selectPageByOrderIds(MemberUserPageReqVO reqVO, List<Long> orderIds) {
        // 处理 tagIds 过滤条件
        String tagIdSql = "";
        if (CollUtil.isNotEmpty(reqVO.getTagIds())) {
            tagIdSql = reqVO.getTagIds().stream()
                    .map(tagId -> "FIND_IN_SET(" + tagId + ", tag_ids)")
                    .collect(Collectors.joining(" OR "));
        }
        // 分页查询
        return selectPage(reqVO, new LambdaQueryWrapperX<MemberUserDO>()
                .inIfPresent(MemberUserDO::getId, orderIds)
                .likeIfPresent(MemberUserDO::getMobile, reqVO.getMobile())
                .betweenIfPresent(MemberUserDO::getLoginDate, reqVO.getLoginDate())
                .likeIfPresent(MemberUserDO::getNickname, reqVO.getNickname())
                .betweenIfPresent(MemberUserDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(MemberUserDO::getLevelId, reqVO.getLevelId())
                .eqIfPresent(MemberUserDO::getGroupId, reqVO.getGroupId())
                .apply(StrUtil.isNotEmpty(tagIdSql), tagIdSql)
                .orderByDesc(MemberUserDO::getId));
    }

    /**
     * 分页查询会员用户，支持部门数据隔离（按用户ID过滤）
     *
     * @param reqVO 查询参数
     * @param userIds 允许查询的用户ID列表
     * @return 分页结果
     */
    default PageResult<MemberUserDO> selectPageByUserIds(MemberUserPageReqVO reqVO, List<Long> userIds) {
        // 处理 tagIds 过滤条件
        String tagIdSql = "";
        if (CollUtil.isNotEmpty(reqVO.getTagIds())) {
            tagIdSql = reqVO.getTagIds().stream()
                    .map(tagId -> "FIND_IN_SET(" + tagId + ", tag_ids)")
                    .collect(Collectors.joining(" OR "));
        }
        // 分页查询
        return selectPage(reqVO, new LambdaQueryWrapperX<MemberUserDO>()
                .inIfPresent(MemberUserDO::getId, userIds)
                .likeIfPresent(MemberUserDO::getMobile, reqVO.getMobile())
                .betweenIfPresent(MemberUserDO::getLoginDate, reqVO.getLoginDate())
                .likeIfPresent(MemberUserDO::getNickname, reqVO.getNickname())
                .betweenIfPresent(MemberUserDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(MemberUserDO::getLevelId, reqVO.getLevelId())
                .eqIfPresent(MemberUserDO::getGroupId, reqVO.getGroupId())
                .apply(StrUtil.isNotEmpty(tagIdSql), tagIdSql)
                .orderByDesc(MemberUserDO::getId));
    }

    default Long selectCountByGroupId(Long groupId) {
        return selectCount(MemberUserDO::getGroupId, groupId);
    }

    default Long selectCountByLevelId(Long levelId) {
        return selectCount(MemberUserDO::getLevelId, levelId);
    }

    default Long selectCountByTagId(Long tagId) {
        return selectCount(new LambdaQueryWrapperX<MemberUserDO>()
                .apply("FIND_IN_SET({0}, tag_ids)", tagId));
    }

    /**
     * 更新用户积分（增加）
     *
     * @param id        用户编号
     * @param incrCount 增加积分（正数）
     */
    default void updatePointIncr(Long id, Integer incrCount) {
        Assert.isTrue(incrCount > 0);
        LambdaUpdateWrapper<MemberUserDO> lambdaUpdateWrapper = new LambdaUpdateWrapper<MemberUserDO>()
                .setSql(" point = point + " + incrCount)
                .eq(MemberUserDO::getId, id);
        update(null, lambdaUpdateWrapper);
    }

    /**
     * 更新用户积分（减少）
     *
     * @param id        用户编号
     * @param incrCount 增加积分（负数）
     * @return 更新行数
     */
    default int updatePointDecr(Long id, Integer incrCount) {
        Assert.isTrue(incrCount < 0);
        LambdaUpdateWrapper<MemberUserDO> lambdaUpdateWrapper = new LambdaUpdateWrapper<MemberUserDO>()
                .setSql(" point = point + " + incrCount) // 负数，所以使用 + 号
                .eq(MemberUserDO::getId, id);
        return update(null, lambdaUpdateWrapper);
    }

}
