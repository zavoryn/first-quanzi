import request from '@/config/axios'

// 好友 VO
export interface ImFriendVO {
  id: number // id
  userId: number // 用户id
  accountId: number // 授权账号Id
  friendId: number // 好友id
  friendNickName: string // 好友昵称
  friendHeadImage: string // 好友头像
  follow: string // 特别关注
  source: string // 来源
  touchNum: string // 触达次数
  postName: string // 岗位
  phone: string // 电话
  email: string // email
  remark: string // 备注
  friendTag: string // 标签
  autoReply: string // AI关键词自动回复
  autoReplyData: string // AI快捷回复数据
  forwardAuto: string // 是否自动转发
  forwardUser: string // 转发用户信息
  corpId: number // 企业ID
  corpName: string // 企业简称
  corpDesc: string // 企业描述
  corpFullName: string // 企业全称
  createTime: Date // 创建时间
  addTime: Date // 添加好友时间
  accountName: string // 授权账号名称
}

// 好友 API
export const ImFriendApi = {
  // 查询好友分页
  getImFriendPage: async (params: any) => {
    return await request.get({ url: `/kaifa/im-friend/page`, params })
  },

  // 查询好友详情
  getImFriend: async (id: number) => {
    return await request.get({ url: `/kaifa/im-friend/get?id=` + id })
  },

  // 新增好友
  createImFriend: async (data: ImFriendVO) => {
    return await request.post({ url: `/kaifa/im-friend/create`, data })
  },

  // 修改好友
  updateImFriend: async (data: ImFriendVO) => {
    return await request.put({ url: `/kaifa/im-friend/update`, data })
  },

  // 删除好友
  deleteImFriend: async (id: number) => {
    return await request.delete({ url: `/kaifa/im-friend/delete?id=` + id })
  },

  // 删除好友
  deleteImFriends: async (ids) => {
    return await request.delete({ url: `/kaifa/im-friend/deletes?ids=` + ids})
  },

  // 导出好友 Excel
  exportImFriend: async (params) => {
    return await request.download({ url: `/kaifa/im-friend/export-excel`, params })
  },
}
