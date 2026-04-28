import request from '@/config/axios'

// 邮件记录 VO
export interface ImTaskRecordVO {
  id: number // 系统主键
  taskId: string // 任务Id
  sendUserId: number // 发送人id
  sendUserName: string // 发送人
  content: string // 发送内容
  url: string // 附件，待定
  sendTime: Date // 已发送时间
  accountId: number // 企业id
  receiveUserId: number // 接收人id
  receiveUserName: string // 接收人
  status: string // 状态(0-待发送 1-已发送 2-发送失败)
}

// 邮件记录 API
export const ImTaskRecordApi = {
  // 查询邮件记录分页
  getImTaskRecordPage: async (params: any) => {
    return await request.get({ url: `/kaifa/im-task-record/page`, params })
  },

  // 查询邮件记录详情
  getImTaskRecord: async (id: number) => {
    return await request.get({ url: `/kaifa/im-task-record/get?id=` + id })
  },

  // 新增邮件记录
  createImTaskRecord: async (data: ImTaskRecordVO) => {
    return await request.post({ url: `/kaifa/im-task-record/create`, data })
  },

  // 修改邮件记录
  updateImTaskRecord: async (data: ImTaskRecordVO) => {
    return await request.put({ url: `/kaifa/im-task-record/update`, data })
  },

  // 删除邮件记录
  deleteImTaskRecord: async (id: number) => {
    return await request.delete({ url: `/kaifa/im-task-record/delete?id=` + id })
  },

  // 导出邮件记录 Excel
  exportImTaskRecord: async (params) => {
    return await request.download({ url: `/kaifa/im-task-record/export-excel`, params })
  },
}