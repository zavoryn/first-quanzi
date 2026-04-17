import request from '@/config/axios'

// 服务配置 VO
export interface ServiceConfigVO {
  id: number // id
  name: string // 名称
  type: string // 类型
  param: string // 参数
}

// 服务配置 API
export const ServiceConfigApi = {
  // 查询服务配置分页
  getServiceConfigPage: async (params: any) => {
    return await request.get({ url: `/heal/service-config/page`, params })
  },

  // 查询服务配置详情
  getServiceConfig: async (id: number) => {
    return await request.get({ url: `/heal/service-config/get?id=` + id })
  },

  // 新增服务配置
  createServiceConfig: async (data: ServiceConfigVO) => {
    return await request.post({ url: `/heal/service-config/create`, data })
  },

  // 修改服务配置
  updateServiceConfig: async (data: ServiceConfigVO) => {
    return await request.put({ url: `/heal/service-config/update`, data })
  },

  // 删除服务配置
  deleteServiceConfig: async (id: number) => {
    return await request.delete({ url: `/heal/service-config/delete?id=` + id })
  },

  // 导出服务配置 Excel
  exportServiceConfig: async (params) => {
    return await request.download({ url: `/heal/service-config/export-excel`, params })
  },
}