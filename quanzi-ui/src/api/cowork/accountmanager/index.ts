import request from '@/config/axios'

// 企业用户 VO
export interface ImCorpUserVO {
  id: number // id
  sysUserId: number // 系统用户ID
  userName: string // 用户名
  nickName: string // 用户昵称
  headImage: string // 用户头像
  headImageThumb: string // 用户头像缩略图
  password: string // 密码
  sex: boolean // 性别 0:男 1:女
  isBanned: boolean // 是否被封禁 0:否 1:是
  reason: string // 被封禁原因
  type: number // 用户类型 1:普通用户 2:审核账户 3:社媒用户
  status: string // 账号状态
  signature: string // 个性签名
  lastLoginTime: Date // 最后登录时间
  createdTime: Date // 创建时间
  vid: number // 第三方企业微信唯一ID
  uuid: string // 企业微信UUID，登录失效会变
  acctid: string // acctid
  englishName: string // english_name
  mobile: string // mobile
  position: string // position
  corpId: number // 企业ID
  corpName: string // 企业简称
  corpDesc: string // 企业描述
  corpFullName: string // 企业全称
  bodyJson: string // 数据Json
}

// 企业用户 API
export const ImCorpUserApi = {
  // 查询企业用户分页
  getImCorpUserPage: async (params: any) => {
    return await request.get({ url: `/kaifa/im-corp-user/page`, params })
  },

  // 查询企业用户详情
  getImCorpUser: async (id: number) => {
    return await request.get({ url: `/kaifa/im-corp-user/get?id=` + id })
  },

  // 新增企业用户
  createImCorpUser: async (data: ImCorpUserVO) => {
    return await request.post({ url: `/kaifa/im-corp-user/create`, data })
  },

  // 修改企业用户
  updateImCorpUser: async (data: ImCorpUserVO) => {
    return await request.put({ url: `/kaifa/im-corp-user/update`, data })
  },

  // 删除企业用户
  deleteImCorpUser: async (id: number) => {
    return await request.delete({ url: `/kaifa/im-corp-user/delete?id=` + id })
  },

  // 导出企业用户 Excel
  exportImCorpUser: async (params) => {
    return await request.download({ url: `/kaifa/im-corp-user/export-excel`, params })
  },
  // 下发采集
  getFriend: async (data) => {
    return await request.post({ url: `/kaifa/im-corp-user/getFriend`, data })
  },
  handleUser: async (data) => {
    return await request.post({ url: `/kaifa/im-corp-user/handleUser`, data })
  },
  generateWork: async (data) => {
    return await request.post({ url: `/kaifa/im-corp-user/generateWork`, data })
  }
}
