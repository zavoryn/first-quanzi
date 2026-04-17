import request from '@/config/axios'

// 健康知识 VO
export interface KnowledgeVO {
  id: number // id
  author: string // 作者
  title: string // 标题
  coverUrl: string // 封面url
  content: string // 内容
  linkUrl: string // 网页链接
  sort: number // 排序
  shareNum: number // 分享个数
  likeNum: number // 点赞个数
  commentNum: number // 评论个数
  visitNum: number // 访问次数
}

// 健康知识 API
export const KnowledgeApi = {
  // 查询健康知识分页
  getKnowledgePage: async (params: any) => {
    return await request.get({ url: `/heal/knowledge/page`, params })
  },

  // 查询健康知识详情
  getKnowledge: async (id: number) => {
    return await request.get({ url: `/heal/knowledge/get?id=` + id })
  },

  // 新增健康知识
  createKnowledge: async (data: KnowledgeVO) => {
    return await request.post({ url: `/heal/knowledge/create`, data })
  },

  // 修改健康知识
  updateKnowledge: async (data: KnowledgeVO) => {
    return await request.put({ url: `/heal/knowledge/update`, data })
  },

  // 删除健康知识
  deleteKnowledge: async (id: number) => {
    return await request.delete({ url: `/heal/knowledge/delete?id=` + id })
  },

  // 导出健康知识 Excel
  exportKnowledge: async (params) => {
    return await request.download({ url: `/heal/knowledge/export-excel`, params })
  },
}