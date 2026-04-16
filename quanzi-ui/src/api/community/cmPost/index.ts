import request from '@/config/axios'

// 用户发帖详情 VO
export interface CmPostVO {
  id: number // 帖子ID
  userId: number // 用户ID
  topicId: number // 圈子ID
  discussId: number // 话题ID
  voteId: number // 投票ID
  title: string // 标题
  content: string // 内容
  media: string // 文件
  readCount: number // 浏览量
  postTop: number // 置顶
  type: number // 帖子类型1图文2文章
  address: string // 地址
  longitude: number // 经度
  latitude: number // 纬度
  status: number // 状态0正常1审核2草稿3定时发布
  sendTime: Date // 发布时间
  remark: string // 备注
  cateId: number // 分类
}

// 用户发帖详情 API
export const CmPostApi = {
  // 查询用户发帖详情分页
  getCmPostPage: async (params: any) => {
    return await request.get({ url: `/community/cm-post/page`, params })
  },

  // 查询用户发帖详情详情
  getCmPost: async (id: number) => {
    return await request.get({ url: `/community/cm-post/get?id=` + id })
  },

  // 新增用户发帖详情
  createCmPost: async (data: CmPostVO) => {
    return await request.post({ url: `/community/cm-post/create`, data })
  },

  // 修改用户发帖详情
  updateCmPost: async (data: CmPostVO) => {
    return await request.put({ url: `/community/cm-post/update`, data })
  },

  // 删除用户发帖详情
  deleteCmPost: async (id: number) => {
    return await request.delete({ url: `/community/cm-post/delete?id=` + id })
  },

  // 导出用户发帖详情 Excel
  exportCmPost: async (params) => {
    return await request.download({ url: `/community/cm-post/export-excel`, params })
  },
}