import request from '@/config/axios'

// 标签组 VO
export interface ImTaggroupVO {
  id: number // 主键
  groupId: string // 企业微信返回的id
  groupName: string // 标签组名
  groupTagType: number // 标签分组类型(1:客户企业标签;2:客户个人标签3:群标签;)
  owner: string // 标签所属人
  tagSource: string // 标签源数据
}

// 标签组 API
export const ImTaggroupApi = {
  // 查询标签组分页
  getImTaggroupPage: async (params: any) => {
    return await request.get({ url: `/kaifa/im-taggroup/page`, params })
  },

  // 查询标签组详情
  getImTaggroup: async (id: number) => {
    return await request.get({ url: `/kaifa/im-taggroup/get?id=` + id })
  },

  // 新增标签组
  createImTaggroup: async (data: ImTaggroupVO) => {
    return await request.post({ url: `/kaifa/im-taggroup/create`, data })
  },

  // 修改标签组
  updateImTaggroup: async (data: ImTaggroupVO) => {
    return await request.put({ url: `/kaifa/im-taggroup/update`, data })
  },

  // 删除标签组
  deleteImTaggroup: async (id: number) => {
    return await request.delete({ url: `/kaifa/im-taggroup/delete?id=` + id })
  },

  // 导出标签组 Excel
  exportImTaggroup: async (params) => {
    return await request.download({ url: `/kaifa/im-taggroup/export-excel`, params })
  },
  //同步标签组
  syncTagGroupApi: async () => {
    return await request.get({ url: `/kaifa/im-taggroup/syncTagGroup`})
  },
}
