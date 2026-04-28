import request from '@/config/axios'

// 企业微信标签 VO
export interface ImTagVO {
  id: number // 主键
  tagId: string // 微信端返回的id
  groupId: string // 标签组id
  name: string // 标签名
  tagType: number // 标签分组类型1:客户企业标签;2:客户个人标签;3:群标签
  tagSource: string // 标签源数据
  owner: string // 标签所属人
}

// 企业微信标签 API
export const ImTagApi = {
  // 查询企业微信标签分页
  getImTagPage: async (params: any) => {
    return await request.get({ url: `/kaifa/im-tag/page`, params })
  },

  // 查询企业微信标签详情
  getImTag: async (id: number) => {
    return await request.get({ url: `/kaifa/im-tag/get?id=` + id })
  },

  // 新增企业微信标签
  createImTag: async (data: ImTagVO) => {
    return await request.post({ url: `/kaifa/im-tag/create`, data })
  },

  // 修改企业微信标签
  updateImTag: async (data: ImTagVO) => {
    return await request.put({ url: `/kaifa/im-tag/update`, data })
  },

  // 删除企业微信标签
  deleteImTag: async (id: number) => {
    return await request.delete({ url: `/kaifa/im-tag/delete?id=` + id })
  },

  // 导出企业微信标签 Excel
  exportImTag: async (params) => {
    return await request.download({ url: `/kaifa/im-tag/export-excel`, params })
  },
  getImTagList: async (params: any) => {
    return await request.get({ url: `/kaifa/im-tag/list`, params })
  },
}
