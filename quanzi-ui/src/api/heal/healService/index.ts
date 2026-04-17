import request from '@/config/axios'

// 服务列 VO
export interface ServiceVO {
  id: number // id
  title: string // 标题
  introduce: string // 简介
  coverUrl: string // 封面url
  content: string // 内容
  linkUrl: string // 网页链接
  fee: number // 价格
  sort: number // 排序
  shareNum: number // 分享个数
  likeNum: number // 点赞个数
  commentNum: number // 评论个数
  visitNum: number // 访问次数
}

// 服务列 API
export const ServiceApi = {
  // 查询服务列分页
  getServicePage: async (params: any) => {
    return await request.get({ url: `/heal/service/page`, params })
  },

  // 查询服务列详情
  getService: async (id: number) => {
    return await request.get({ url: `/heal/service/get?id=` + id })
  },

  // 新增服务列
  createService: async (data: ServiceVO) => {
    return await request.post({ url: `/heal/service/create`, data })
  },

  // 修改服务列
  updateService: async (data: ServiceVO) => {
    return await request.put({ url: `/heal/service/update`, data })
  },

  // 删除服务列
  deleteService: async (id: number) => {
    return await request.delete({ url: `/heal/service/delete?id=` + id })
  },

  // 导出服务列 Excel
  exportService: async (params) => {
    return await request.download({ url: `/heal/service/export-excel`, params })
  },
}