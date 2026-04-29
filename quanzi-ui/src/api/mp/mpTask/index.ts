import request from '@/config/axios'

// 自动开发公众号信息 VO
export interface TaskVO {
  id: number // 系统主键
  taskId: string // 任务id
  templateId: number // 模板id==待定
  taskName: string // 任务名称
  title: string // 主题，目标
  chatType: number // 群发任务的类，1：主题，2：目标，3：随意
  isTask: number // 是否定时任务 0 立即发送 1 定时发送2 每天发送
  isRules: number // 发送规则1草稿2审核0发送
  sender: string // 发送人昵称
  sendUserId: number // 发送人
  sendTime: string // 发送时间
  status: string // 状态0进行中1结束任务2任务暂停
  remark: string // 备注
  fromDuration: string // 指定时间
}

// 自动开发公众号信息 API
export const TaskApi = {
  // 查询自动开发公众号信息分页  列表
  getTaskPage: async (params: any) => {
    return await request.get({ url: `/mp/task/page`, params })
  },

  // 查询自动开发公众号信息详情
  getTask: async (id: number | string) => {
    return await request.get({ url: `/mp/task/get?id=` + id })
  },

  // 新增自动开发公众号信息  新增
  createTask: async (data: any) => {
    return await request.post({ url: `/mp/task/create`, data })
  },

  // 修改自动开发公众号信息
  updateTask: async (data: any) => {
    return await request.put({ url: `/mp/task/update`, data })
  },

  // 删除自动开发公众号信息  删除
  deleteTask: async (id: number) => {
    return await request.delete({ url: `/mp/task/delete?id=` + id })
  },

  // 导出自动开发公众号信息 Excel
  exportTask: async (params) => {
    return await request.download({ url: `/mp/task/export-excel`, params })
  },

   // 修改发送状态   id  status
  updateTaskStatus: async (data: any) => {
    return await request.put({ url: `/mp/task/status`, data })
  },
}
