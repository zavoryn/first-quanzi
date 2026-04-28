import request from '@/config/axios'

// IM回复配置 VO
export interface ImReplyConfigVO {
  id: number // 系统主键
  type: string // 回复类型(corp 企业 customer 个人)
  category: string // 回复分类(text 文本 file 文件，mini小程序)
  title: string // 标题
  content: string // 内容(JSON)
  status: string // 状态
}

// IM回复配置 API
export const ImReplyConfigApi = {
  // 查询IM回复配置分页
  getImReplyConfigPage: async (params: any) => {
    return await request.get({ url: `/kaifa/im-reply-config/page`, params })
  },

  // 查询IM回复配置详情
  getImReplyConfig: async (id: number) => {
    return await request.get({ url: `/kaifa/im-reply-config/get?id=` + id })
  },

  // 新增IM回复配置
  createImReplyConfig: async (data: ImReplyConfigVO) => {
    return await request.post({ url: `/kaifa/im-reply-config/create`, data })
  },

  // 修改IM回复配置
  updateImReplyConfig: async (data: ImReplyConfigVO) => {
    return await request.put({ url: `/kaifa/im-reply-config/update`, data })
  },

  // 删除IM回复配置
  deleteImReplyConfig: async (id: number) => {
    return await request.delete({ url: `/kaifa/im-reply-config/delete?id=` + id })
  },

  // 导出IM回复配置 Excel
  exportImReplyConfig: async (params) => {
    return await request.download({ url: `/kaifa/im-reply-config/export-excel`, params })
  },
}
