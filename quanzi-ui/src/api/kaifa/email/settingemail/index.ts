import request from '@/config/axios'

// 邮件配置 VO
export interface SettingEmailVO {
  id: number // 系统主键
  emailType: string // 邮箱类型(1-腾讯qq 2-网易163 3-阿里个人版)
  email: string // 邮件地址
  name: string // 邮件昵称
  password: string // 邮件密码
  host: string // 收邮件HOST
  port: number // 收邮件端口
  ssl: string // 收邮件SSL
  outHost: string // 写邮件HOST
  outPort: number // 写邮件端口
  outSsl: string // 写邮件SSL
  remark: string // 备注
  isYw: string // 1-业务邮箱
  isUser: number // 是否启用
  userId: number // 用户ID
  status: string // 状态(0-正常 1-异常)
}

// 邮件配置 API
export const SettingEmailApi = {
  // 查询邮件配置分页
  getSettingEmailPage: async (params: any) => {
    return await request.get({ url: `/kaifa/setting-email/page`, params })
  },

  // 查询邮件配置详情
  getSettingEmail: async (id: number) => {
    return await request.get({ url: `/kaifa/setting-email/get?id=` + id })
  },

  // 新增邮件配置
  createSettingEmail: async (data: SettingEmailVO) => {
    return await request.post({ url: `/kaifa/setting-email/create`, data })
  },

  // 修改邮件配置
  updateSettingEmail: async (data: SettingEmailVO) => {
    return await request.put({ url: `/kaifa/setting-email/update`, data })
  },

  // 删除邮件配置
  deleteSettingEmail: async (id: number) => {
    return await request.delete({ url: `/kaifa/setting-email/delete?id=` + id })
  },

  // 导出邮件配置 Excel
  exportSettingEmail: async (params) => {
    return await request.download({ url: `/kaifa/setting-email/export-excel`, params })
  },
}