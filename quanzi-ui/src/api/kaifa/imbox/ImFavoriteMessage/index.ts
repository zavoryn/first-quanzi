import request from '@/config/axios'

// 群消息 VO
export interface ImFavoriteMessageVO {
  id: number // id
  sysUserId: number // 系统用户ID
  sendNickName: string // 发送用户昵称
  content: string // 发送内容
  type: number // 消息类型 0:文字 1:图片 2:文件 3:语音 4:视频 21:提示
  sendTime: Date // 发送时间
  dataJson: string // 消息数据
}

// 群消息 API
export const ImFavoriteMessageApi = {
  // 查询群消息分页
  getImFavoriteMessagePage: async (params: any) => {
    return await request.get({ url: `/kaifa/im-favorite-message/page`, params })
  },

  // 查询群消息详情
  getImFavoriteMessage: async (id: number) => {
    return await request.get({ url: `/kaifa/im-favorite-message/get?id=` + id })
  },

  // 新增群消息
  createImFavoriteMessage: async (data: ImFavoriteMessageVO) => {
    return await request.post({ url: `/kaifa/im-favorite-message/create`, data })
  },

  // 修改群消息
  updateImFavoriteMessage: async (data: ImFavoriteMessageVO) => {
    return await request.put({ url: `/kaifa/im-favorite-message/update`, data })
  },

  // 删除群消息
  deleteImFavoriteMessage: async (id: number) => {
    return await request.delete({ url: `/kaifa/im-favorite-message/delete?id=` + id })
  },

  // 导出群消息 Excel
  exportImFavoriteMessage: async (params) => {
    return await request.download({ url: `/kaifa/im-favorite-message/export-excel`, params })
  },
}