import request from '@/config/axios'

// SOP节日 VO
export interface ImSopFestivalVO {
  id: number // 系统主键
  festDate: string // 节日日期
  comapny: string // 国家
  festName: string // 节日名称
  status: string // 状态
}

// SOP节日 API
export const ImSopFestivalApi = {
  // 查询SOP节日分页
  getImSopFestivalPage: async (params: any) => {
    return await request.get({ url: `/kaifa/im-sop-festival/page`, params })
  },

  // 查询SOP节日详情
  getImSopFestival: async (id: number) => {
    return await request.get({ url: `/kaifa/im-sop-festival/get?id=` + id })
  },

  // 新增SOP节日
  createImSopFestival: async (data: ImSopFestivalVO) => {
    return await request.post({ url: `/kaifa/im-sop-festival/create`, data })
  },

  // 修改SOP节日
  updateImSopFestival: async (data: ImSopFestivalVO) => {
    return await request.put({ url: `/kaifa/im-sop-festival/update`, data })
  },

  // 删除SOP节日
  deleteImSopFestival: async (id: number) => {
    return await request.delete({ url: `/kaifa/im-sop-festival/delete?id=` + id })
  },

  // 导出SOP节日 Excel
  exportImSopFestival: async (params) => {
    return await request.download({ url: `/kaifa/im-sop-festival/export-excel`, params })
  },
}
