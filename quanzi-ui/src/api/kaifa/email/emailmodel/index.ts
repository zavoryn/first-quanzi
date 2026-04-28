import request from '@/config/axios'

// 模板-快速文本 VO
export interface EmailModelVO {
  id: number // 系统主键
  type: string // 类型（model个人模板、text快速文本)
  title: string // 标题
  content: string // 内容
  conteText: string // 文本内容(摘要)
  status: string // 状态
}

// 模板-快速文本 API
export const EmailModelApi = {
  // 查询模板-快速文本分页
  getEmailModelPage: async (params: any) => {
    return await request.get({ url: `/kaifa/email-model/page`, params })
  },

  // 查询模板-快速文本详情
  getEmailModel: async (id: number) => {
    return await request.get({ url: `/kaifa/email-model/get?id=` + id })
  },

  // 新增模板-快速文本
  createEmailModel: async (data: EmailModelVO) => {
    return await request.post({ url: `/kaifa/email-model/create`, data })
  },

  // 修改模板-快速文本
  updateEmailModel: async (data: EmailModelVO) => {
    return await request.put({ url: `/kaifa/email-model/update`, data })
  },

  // 删除模板-快速文本
  deleteEmailModel: async (id: number) => {
    return await request.delete({ url: `/kaifa/email-model/delete?id=` + id })
  },

  // 导出模板-快速文本 Excel
  exportEmailModel: async (params) => {
    return await request.download({ url: `/kaifa/email-model/export-excel`, params })
  },
}
