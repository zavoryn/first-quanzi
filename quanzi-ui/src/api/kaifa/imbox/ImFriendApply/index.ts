import request from '@/config/axios'

// IM用户申请 VO
export interface ImFriendApplyVO {
  id: number // id
  sendId: number // 发送人
  recvId: number // 接收人
  sysUserId: number // 用户Id
  nickname: string // 用户昵称
  sex: number // 用户性别
  avatar: string // 用户头像
  corpId: string // 企业Id
  corpName: string // 企业简称
  corpDesc: string // 企业描述
  corpFullName: string // 企业名称
  status: string // 用户状态
  dataJson: string // dataJson
}

// IM用户申请 API
export const ImFriendApplyApi = {
  // 查询IM用户申请分页
  getImFriendApplyPage: async (params: any) => {
    return await request.get({ url: `/kaifa/im-friend-apply/page`, params })
  },

  // 查询IM用户申请详情
  getImFriendApply: async (id: number) => {
    return await request.get({ url: `/kaifa/im-friend-apply/get?id=` + id })
  },

  // 新增IM用户申请
  createImFriendApply: async (data: ImFriendApplyVO) => {
    return await request.post({ url: `/kaifa/im-friend-apply/create`, data })
  },

  // 修改IM用户申请
  updateImFriendApply: async (data: ImFriendApplyVO) => {
    return await request.put({ url: `/kaifa/im-friend-apply/update`, data })
  },

  // 删除IM用户申请
  deleteImFriendApply: async (id: number) => {
    return await request.delete({ url: `/kaifa/im-friend-apply/delete?id=` + id })
  },

  // 导出IM用户申请 Excel
  exportImFriendApply: async (params) => {
    return await request.download({ url: `/kaifa/im-friend-apply/export-excel`, params })
  },
}