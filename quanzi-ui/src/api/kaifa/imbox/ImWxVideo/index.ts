import request from '@/config/axios'

// IM视频号 VO
export interface ImWxVideoVO {
  id: number // id
  thumbUrl: string // 缩略图
  coverUrl: string // 封面图
  sender: string // 发送人
  senderName: string // 发送人昵称
  receiver: string // 接收人
  isRoom: number // 是否群组
  sendTime: string // 发送时间
  nickname: string // 视频号昵称
  avatar: string // 视频号头像
  url: string // 视频号链接
  desc: string // 视频号描述
  flag: string // flag
  issync: string // issync
  extras: string // extras
  appInfo: string // app_info
  referid: number // referid
  msgId: number // msg_id
  serverId: number // server_id
  objectId: string // object_id
  msgtype: string // msgtype
  objectNonceId: string // object_nonce_id
  readuinscount: number // readuinscount
}

// IM视频号 API
export const ImWxVideoApi = {
  // 查询IM视频号分页
  getImWxVideoPage: async (params: any) => {
    return await request.get({ url: `/kaifa/im-wx-video/page`, params })
  },

  // 查询IM视频号详情
  getImWxVideo: async (id: number) => {
    return await request.get({ url: `/kaifa/im-wx-video/get?id=` + id })
  },

  // 新增IM视频号
  createImWxVideo: async (data: ImWxVideoVO) => {
    return await request.post({ url: `/kaifa/im-wx-video/create`, data })
  },

  // 修改IM视频号
  updateImWxVideo: async (data: ImWxVideoVO) => {
    return await request.put({ url: `/kaifa/im-wx-video/update`, data })
  },

  // 删除IM视频号
  deleteImWxVideo: async (id: number) => {
    return await request.delete({ url: `/kaifa/im-wx-video/delete?id=` + id })
  },

  // 删除IM视频号
  deleteImWxVideos: async (ids) => {
    return await request.delete({ url: `/kaifa/im-wx-video/deletes?ids=` + ids })
  },

  // 导出IM视频号 Excel
  exportImWxVideo: async (params) => {
    return await request.download({ url: `/kaifa/im-wx-video/export-excel`, params })
  },
}
