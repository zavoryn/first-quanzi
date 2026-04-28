import request from '@/config/axios'

// 平台租户授权 VO
export interface ExPlatformTenantVO {
  id: number // 系统主键
  platformId: number // 平台ID
  remark: string // 备注
  delFlag: number // 删除标志(0正常 1删除	
  createBy: number // 创建者
  updateBy: number // 更新者
}

// 平台租户授权 API
export const ExPlatformTenantApi = {
  // 查询平台租户授权分页
  getExPlatformTenantPage: async (params: any) => {
    return await request.get({ url: `/kaifa/ex-platform-tenant/page`, params })
  },

  // 查询平台租户授权详情
  getExPlatformTenant: async (id: number) => {
    return await request.get({ url: `/kaifa/ex-platform-tenant/get?id=` + id })
  },

  // 新增平台租户授权
  createExPlatformTenant: async (data: ExPlatformTenantVO) => {
    return await request.post({ url: `/kaifa/ex-platform-tenant/create`, data })
  },

  // 修改平台租户授权
  updateExPlatformTenant: async (data: ExPlatformTenantVO) => {
    return await request.put({ url: `/kaifa/ex-platform-tenant/update`, data })
  },

  // 删除平台租户授权
  deleteExPlatformTenant: async (id: number) => {
    return await request.delete({ url: `/kaifa/ex-platform-tenant/delete?id=` + id })
  },

  // 导出平台租户授权 Excel
  exportExPlatformTenant: async (params) => {
    return await request.download({ url: `/kaifa/ex-platform-tenant/export-excel`, params })
  },
}