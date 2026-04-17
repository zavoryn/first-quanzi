import request from '@/config/axios'

// 首页banner VO
export interface BannerVO {
  id: number // id
  title: string // 标题
  imageUrl: string // 图片地址
  linkUrl: string // 链接地址
  status: string // 上下线 1（true）上线， 0（false）下线
  sort: number // 排序
  type: number // 0 : banner    1 : notice
  remark: string // 备注
  subject: string // 网页类型
  param: string // 参数
}

// 首页banner API
export const BannerApi = {
  // 查询首页banner分页
  getBannerPage: async (params: any) => {
    return await request.get({ url: `/heal/banner/page`, params })
  },

  // 查询首页banner详情
  getBanner: async (id: number) => {
    return await request.get({ url: `/heal/banner/get?id=` + id })
  },

  // 新增首页banner
  createBanner: async (data: BannerVO) => {
    return await request.post({ url: `/heal/banner/create`, data })
  },

  // 修改首页banner
  updateBanner: async (data: BannerVO) => {
    return await request.put({ url: `/heal/banner/update`, data })
  },

  // 删除首页banner
  deleteBanner: async (id: number) => {
    return await request.delete({ url: `/heal/banner/delete?id=` + id })
  },

  // 导出首页banner Excel
  exportBanner: async (params) => {
    return await request.download({ url: `/heal/banner/export-excel`, params })
  },
}