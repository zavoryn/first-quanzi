import request from '@/config/axios'

// 公众号发送记录 VO
export interface TaskRecordVO {
  id: number // 系统主键
  taskId: string // 任务Id
  sendUserId: string // 发送人id
  sendUserName: string // 发送人
  content: string // 发送内容
  url: string // 附件，待定
  sendTime: Date // 已发送时间
  receiveUserId: string // 接收人id
  receiveUserName: string // 接收人
  status: string // 状态(0-待发送 1-已发送 2-发送失败)
}

// 公众号发送记录 API
export const TaskRecordApi = {
  // 查询公众号发送记录分页
  getTaskRecordPage: async (params: any) => {
    return await request.get({ url: `/mp/task-record/page`, params })
  },

  // 查询公众号发送记录详情  查看编辑内容
  getTaskRecord: async (id: number | string) => {
    return await request.get({ url: `/mp/task-record/get?id=` + id })
  },

  // 新增公众号发送记录
  createTaskRecord: async (data: TaskRecordVO) => {
    return await request.post({ url: `/mp/task-record/create`, data })
  },

  // 修改公众号发送记录
  updateTaskRecord: async (data: TaskRecordVO) => {
    return await request.put({ url: `/mp/task-record/update`, data })
  },

  // 删除公众号发送记录
  deleteTaskRecord: async (id: number) => {
    return await request.delete({ url: `/mp/task-record/delete?id=` + id })
  },

  // 导出公众号发送记录 Excel
  exportTaskRecord: async (params) => {
    return await request.download({ url: `/mp/task-record/export-excel`, params })
  },
}
