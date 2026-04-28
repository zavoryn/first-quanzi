import request from '@/config/axios'

// 邮件内容 VO
export interface EmailInfoVO {
  id: number // id
  userId: number // 用户id
  type: string // 类型(1-发送 2-接收 3-草稿 4-垃圾邮件 9-自动发送的)
  title: string // 主题
  content: string // 内容
  contentText: string // 文本内容（摘要）
  fileUrl: string // 附件（多个）
  sendName: string // 发送人
  sendEmail: string // 发送邮箱
  receiveName: string // 接收人
  receiveEmail: string // 接收邮箱
  copyEmail: string // 抄送邮箱
  sendTime: Date // 发送时间
  receiveTime: Date // 接收时间
  handTime: Date // 处理时间
  handStatus: string // 处理状态
  isSee: string // 1-已读
  isTop: string // 是否置顶
  topTime: Date // 置顶时间
  folder: number // 文件夹
  source: string // 来源时区
  sourceTime: Date // 来源更新时间
  isClick: string // 1-已点击
  isTrack: string // 1-追踪
  openCount: number // 打开次数
  openTime: Date // 已打开时间
  openCountry: string // 打开国家
  clickCount: number // 点击次数
  clickTime: Date // 已点击时间
  clickCountry: string // 点击国家
  isRepaly: string // 自动回复邮件
  replayContent: string // 收到的客户回复内容
  replayId: number // 上一步绑定的id,或者回复的id
  emailId: number // 原生邮件id
  tags: string // 标签
  remark: string // 备注
  status: string // 状态
}

// 邮件内容 API
export const EmailInfoApi = {
  // 查询邮件内容分页
  getEmailInfoPage: async (params: any) => {
    return await request.get({ url: `/kaifa/email-info/page`, params })
  },

  // 查询邮件内容详情
  getEmailInfo: async (id: number) => {
    return await request.get({ url: `/kaifa/email-info/get?id=` + id })
  },

  // 新增邮件内容
  createEmailInfo: async (data: EmailInfoVO) => {
    return await request.post({ url: `/kaifa/email-info/create`, data })
  },

  //检查邮箱
  chenkBindEmail: async (email: String) => {
    return await request.get({ url: `/kaifa/email-info/chenkBindEmail?email=`+ email })
  },

  // 修改邮件内容
  updateEmailInfo: async (data: EmailInfoVO) => {
    return await request.put({ url: `/kaifa/email-info/update`, data })
  },

  // 删除邮件内容
  deleteEmailInfo: async (id: number) => {
    return await request.delete({ url: `/kaifa/email-info/delete?id=` + id })
  },

  // 导出邮件内容 Excel
  exportEmailInfo: async (params) => {
    return await request.download({ url: `/kaifa/email-info/export-excel`, params })
  },
}
