import request from '@/config/axios'

// 邮箱预热 VO
export interface SettingSendPreheatVO {
  id: number // 系统主键
  type: string // 收/发
  emailType: string // 邮箱类型
  email: string // 邮箱
  password: string // 邮箱密码/授权码
  host: string // 收邮箱服务器HOST
  port: number // 收邮箱服务器端口
  ssl: string // 收邮箱服务器SSL
  outHost: string // 发邮件HOST
  outPort: number // 发邮件端口
  outSsl: string // 发邮件SSL
  days: number // 预热天数
  dayNum: number // 预热总天数
  sendNum: number // 发送数
  replyNum: number // 回复数量
  replyRatio: number // 回复比例
  maxNum: number // 每日上限
  preheat: string // 预热状态(runing/stoping/computer)
  remark: string // 备注
  status: string // 状态(0-启用 1-停用)
}

// 邮箱预热 API
export const SettingSendPreheatApi = {
  // 查询邮箱预热分页
  getSettingSendPreheatPage: async (params: any) => {
    return await request.get({ url: `/kaifa/setting-send-preheat/page`, params })
  },

  // 查询邮箱预热详情
  getSettingSendPreheat: async (id: number) => {
    return await request.get({ url: `/kaifa/setting-send-preheat/get?id=` + id })
  },

  // 新增邮箱预热
  createSettingSendPreheat: async (data: SettingSendPreheatVO) => {
    return await request.post({ url: `/kaifa/setting-send-preheat/create`, data })
  },

  // 修改邮箱预热
  updateSettingSendPreheat: async (data: SettingSendPreheatVO) => {
    return await request.put({ url: `/kaifa/setting-send-preheat/update`, data })
  },

  // 删除邮箱预热
  deleteSettingSendPreheat: async (id: number) => {
    return await request.delete({ url: `/kaifa/setting-send-preheat/delete?id=` + id })
  },

  // 邮箱预热首页总数
  countSettingSendPreheat: async () => {
    return await request.get({ url: `/kaifa/setting-send-preheat/statPreheat`})
  },
  // 邮箱预热修改状态
  updateStatusSettingSendPreheat: async (data: SettingSendPreheatVO) => {
    return await request.post({ url: `/kaifa/setting-send-preheat/status`, data})
  },

  // 导出邮箱预热 Excel
  exportSettingSendPreheat: async (params) => {
    return await request.download({ url: `/kaifa/setting-send-preheat/export-excel`, params })
  },
}
