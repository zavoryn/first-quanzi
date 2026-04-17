import request from '@/config/axios'

// 首页模块配置 VO
export interface HomeConfigVO {
  id: number // id
  name: string // 标题
  icon: string // 图片地址
  linkUrl: string // 链接地址
  sort: number // 排序
  status: string // 上下线 1（true）上线， 0（false）下线
  remark: string // 备注
  param: string // 参数
}

// 首页模块配置 API
export const HomeConfigApi = {
  // 查询首页模块配置分页
  getHomeConfigPage: async (params: any) => {
    return await request.get({ url: `/heal/home-config/page`, params })
  },

  // 查询首页模块配置详情
  getHomeConfig: async (id: number) => {
    return await request.get({ url: `/heal/home-config/get?id=` + id })
  },

  // 新增首页模块配置
  createHomeConfig: async (data: HomeConfigVO) => {
    return await request.post({ url: `/heal/home-config/create`, data })
  },

  // 修改首页模块配置
  updateHomeConfig: async (data: HomeConfigVO) => {
    return await request.put({ url: `/heal/home-config/update`, data })
  },

  // 删除首页模块配置
  deleteHomeConfig: async (id: number) => {
    return await request.delete({ url: `/heal/home-config/delete?id=` + id })
  },

  // 导出首页模块配置 Excel
  exportHomeConfig: async (params) => {
    return await request.download({ url: `/heal/home-config/export-excel`, params })
  },
}