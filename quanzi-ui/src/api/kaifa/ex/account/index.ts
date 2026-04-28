import request from '@/config/axios'

// 账号 VO
export interface ExAccountVO {
  id: number // 系统主键
  uid: string // 账号Id，唯一标识
  platform: string // 所属平台
  password: string // 密码(token)
  username: string // 用户名
  nickname: string // 昵称
  labels: string // 标签
  sourceName: string // 来源
  avatar: string // 头像
  phone: string // 手机号
  homePage: string // 主页地址
  gender: string // 性别
  regionAddr: string // 地理位置
  description: string // 个人简介
  fansCount: number // 粉丝数
  follows: number // 关注数
  likes: number // 获赞数
  bodyJson: string // 源数据
  bodyImg: string // 源图
  worksNumber: number // 作品数量
  isAuth: string // 是否认证
  authType: string // 认证类型
  authDescription: string // 认证描述
  importTime: Date // 入库时间
  actionTime: Date // 操作时间
  actionNumber: number // 操作次数
  remark: string // 备注
  sort: number // 显示顺序
  status: string // 状态,
  creator: string // 创建人
  creatorName?: string // 创建人名称
  createTime: Date // 创建时间
  updateTime: Date // 更新时间
  updaterName?: string 
}

// 账号 API
export const ExAccountApi = {
  // 查询账号分页
  getExAccountPage: async (params: any) => {
    return await request.get({ url: `/kaifa/ex-account/page`, params })
  },

  // 查询账号详情
  getExAccount: async (id: number) => {
    return await request.get({ url: `/kaifa/ex-account/get?id=` + id })
  },

  // 新增账号
  createExAccount: async (data: ExAccountVO) => {
    return await request.post({ url: `/kaifa/ex-account/create`, data })
  },

  // 修改账号
  updateExAccount: async (data: ExAccountVO) => {
    return await request.put({ url: `/kaifa/ex-account/update`, data })
  },

  // 删除账号
  deleteExAccount: async (id: number) => {
    return await request.delete({ url: `/kaifa/ex-account/delete?id=` + id })
  },

  // 导出账号 Excel
  exportExAccount: async (params) => {
    return await request.download({ url: `/kaifa/ex-account/export-excel`, params })
  },
}
