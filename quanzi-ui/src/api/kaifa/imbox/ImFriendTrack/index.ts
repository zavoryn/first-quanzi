import request from '@/config/axios'

// 客户轨迹 VO
export interface ImFriendTrackVO {
  id: number // id
  friendId: number // 客户id
  type: string // 轨迹类型
  content: string // 轨迹内容
  fileName: string // 轨迹附件名称
  url: string // 轨迹附件地址
}

// 客户轨迹 API
export const ImFriendTrackApi = {
  // 查询客户轨迹分页
  getImFriendTrackPage: async (params: any) => {
    return await request.get({ url: `/kaifa/im-friend-track/page`, params })
  },

  // 查询客户轨迹详情
  getImFriendTrack: async (id: number) => {
    return await request.get({ url: `/kaifa/im-friend-track/get?id=` + id })
  },

  // 新增客户轨迹
  createImFriendTrack: async (data: ImFriendTrackVO) => {
    return await request.post({ url: `/kaifa/im-friend-track/create`, data })
  },

  // 修改客户轨迹
  updateImFriendTrack: async (data: ImFriendTrackVO) => {
    return await request.put({ url: `/kaifa/im-friend-track/update`, data })
  },

  // 删除客户轨迹
  deleteImFriendTrack: async (id: number) => {
    return await request.delete({ url: `/kaifa/im-friend-track/delete?id=` + id })
  },

  // 导出客户轨迹 Excel
  exportImFriendTrack: async (params) => {
    return await request.download({ url: `/kaifa/im-friend-track/export-excel`, params })
  },
}