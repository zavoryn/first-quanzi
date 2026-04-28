import request from '@/config/axios'

// 平台 VO
export interface ExPlatformVO {
  id: number // 系统主键
  code: string // 平台代码
  name: string // 平台名称
  logo: string // 平台LOGO
  valid: number // 登录有效期（单位：小时）
  status: number // 是否启用
  sort: number // 显示顺序
  remark: string // 备注
  deleted: number // 删除标志(0正常 1删除	
  creator: number // 创建者
  updater: number // 更新者
  isDefault: string // 1-默认授权
  number:number
  authorize:Boolean
}

// 平台 API
export const ExPlatformApi = {
  // 查询平台分页
  getExPlatformPage: async (params: any) => {
    return await request.get({ url: `/kaifa/ex-platform/page`, params })
  },

  // 查询全部平台
  getListData: async (params: any) => {
    return await request.get({ url: `/kaifa/ex-platform/listData`, params })
  },

  // 查询平台详情
  getExPlatform: async (id: number) => {
    return await request.get({ url: `/kaifa/ex-platform/get?id=` + id })
  },

  // 新增平台
  createExPlatform: async (data: ExPlatformVO) => {
    return await request.post({ url: `/kaifa/ex-platform/create`, data })
  },

  // 修改平台
  updateExPlatform: async (data: ExPlatformVO) => {
    return await request.put({ url: `/kaifa/ex-platform/update`, data })
  },

  // 删除平台
  deleteExPlatform: async (id: number) => {
    return await request.delete({ url: `/kaifa/ex-platform/delete?id=` + id })
  },

  // 导出平台 Excel
  exportExPlatform: async (params) => {
    return await request.download({ url: `/kaifa/ex-platform/export-excel`, params })
  },

  getExPlatformList: async (params: any) => {
    return await request.get({ url: `/kaifa/ex-platform/list`, params })
  },
}
