import request from '@/config/axios'

// 圈子成员 VO
export interface CmTopicMemberVO {
  id: number // ID
  topicId: number // 圈子ID
  userId: number // 用户ID
  role: number // 角色(0普通成员 1管理员 2创建者)
  orderNum: number // 购买次数
  status: number // 状态(0正常 1审核中 2被拒绝3拉黑)
  muteEndTime: Date // 禁言结束时间
  joinTime: Date // 加入时间
  startTime: Date // 购买时间
  endTime: Date // 到期时间
  type: number // 付费时长1一月2两月3个月4四个月5半年
  interNum: number // 互动次数
  blockRemark: string // 拉黑原因
  remark: string // 会员备注
}

// 圈子成员 API
export const CmTopicMemberApi = {
  // 查询圈子成员分页
  getCmTopicMemberPage: async (params: any) => {
    return await request.get({ url: `/community/cm-topic-member/page`, params })
  },

  // 查询圈子成员详情
  getCmTopicMember: async (id: number) => {
    return await request.get({ url: `/community/cm-topic-member/get?id=` + id })
  },

  // 新增圈子成员
  createCmTopicMember: async (data: CmTopicMemberVO) => {
    return await request.post({ url: `/community/cm-topic-member/create`, data })
  },

  // 修改圈子成员
  updateCmTopicMember: async (data: CmTopicMemberVO) => {
    return await request.put({ url: `/community/cm-topic-member/update`, data })
  },

  // 删除圈子成员
  deleteCmTopicMember: async (id: number) => {
    return await request.delete({ url: `/community/cm-topic-member/delete?id=` + id })
  },

  // 导出圈子成员 Excel
  exportCmTopicMember: async (params) => {
    return await request.download({ url: `/community/cm-topic-member/export-excel`, params })
  },
}