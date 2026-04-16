import request from '@/config/axios'

// 圈子详情 VO
export interface CmTopicVO {
  id: number // 圈子ID
  realName: string // 姓名
  mobile: string // 手机号
  idCard: string // 身份证号
  chatName: string // 微信号/QQ号
  otherName: string // 其他账号名称
  isType: number // 星球类型(0收费星球 1不收费)
  idCardUrl: string // 身份证图片
  certificateNo: string // 投资顾问从业资格证书号
  monthlyPrice: number // 月度价格
  bimonthlyPrice: number // 两月价格
  quarterlyPrice: number // 季度价格
  aprilPrice: number // 四月价格
  halfYearlyPrice: number // 半年价格
  topicName: string // 圈子名称
  coverImage: string // 封面图片
  description: string // 圈子描述
  userId: number // 创建者ID
  memberCount: number // 成员数量
  postCount: number // 帖子数量
  viewCount: number // 浏览量
  isRecommend: number // 是否推荐(0否 1是)
  isHot: number // 是否热门(0否 1是)
  isVisibile: number // 可见范围,0周1一个月2两个月3三个月4半年
  isComment: number // 评论审核(0自由评论 1审核)
  isNo: number // 禁止评论(0否 1是)
  isRenew: number // 续费加入(0自由加入 1审核)
  joinMode: number // 加入方式(0自由加入 1需要审核)
  isMember: number // 展示会员数(0否 1是)
  isQuestion: number // 允许提问(0否 1是)
  isCopy: number // 禁止复制星球内容(0否 1是)
  status: number // 状态(0正常 1审核中 2禁用)
}

// 圈子详情 API
export const CmTopicApi = {
  // 查询圈子详情分页
  getCmTopicPage: async (params: any) => {
    return await request.get({ url: `/community/cm-topic/page`, params })
  },

  // 查询圈子详情详情
  getCmTopic: async (id: number) => {
    return await request.get({ url: `/community/cm-topic/get?id=` + id })
  },

  // 新增圈子详情
  createCmTopic: async (data: CmTopicVO) => {
    return await request.post({ url: `/community/cm-topic/create`, data })
  },

  // 修改圈子详情
  updateCmTopic: async (data: CmTopicVO) => {
    return await request.put({ url: `/community/cm-topic/update`, data })
  },

  // 删除圈子详情
  deleteCmTopic: async (id: number) => {
    return await request.delete({ url: `/community/cm-topic/delete?id=` + id })
  },

  // 导出圈子详情 Excel
  exportCmTopic: async (params) => {
    return await request.download({ url: `/community/cm-topic/export-excel`, params })
  },
}