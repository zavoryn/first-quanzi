import request from '@/config/axios'

// SOP配置 VO
export interface ImSopConfigVO {
  id: number // 系统主键
  sopNo: string // sop编号
  name: string // sop名称
  shortDescribe: string // sop简描述
  sopDescribe: string // sop描述
  content: string // sop内容
  status: string // 状态
}

// SOP配置 API
export const ImSopConfigApi = {
  // 查询SOP配置分页
  getImSopConfigPage: async (params: any) => {
    return await request.get({ url: `/kaifa/im-sop-config/page`, params })
  },

  // 查询SOP配置详情
  getImSopConfig: async (id: number) => {
    return await request.get({ url: `/kaifa/im-sop-config/get?id=` + id })
  },

  // 新增SOP配置
  createImSopConfig: async (data: ImSopConfigVO) => {
    return await request.post({ url: `/kaifa/im-sop-config/create`, data })
  },

  // 修改SOP配置
  updateImSopConfig: async (data: ImSopConfigVO) => {
    return await request.put({ url: `/kaifa/im-sop-config/update`, data })
  },

  // 删除SOP配置
  deleteImSopConfig: async (id: number) => {
    return await request.delete({ url: `/kaifa/im-sop-config/delete?id=` + id })
  },

  // 导出SOP配置 Excel
  exportImSopConfig: async (params) => {
    return await request.download({ url: `/kaifa/im-sop-config/export-excel`, params })
  },
}