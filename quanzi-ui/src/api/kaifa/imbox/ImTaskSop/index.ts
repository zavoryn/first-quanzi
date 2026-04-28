import request from '@/config/axios'

// 自动开发SOP VO
export interface ImTaskSopVO {
  id: number // 系统主键
  sopId: string // sop编码
  sopType: string // sop类型
  taskId: string // 任务Id
  taskInfo: string // sop内容
  loopCount: string // 循环次数(循环型)
  festival: string // 节日编码(节日型)
  calendar: string // 日期时间(日历型)
  status: string // 状态
}

// 自动开发SOP API
export const ImTaskSopApi = {
  // 查询自动开发SOP分页
  getImTaskSopPage: async (params: any) => {
    return await request.get({ url: `/kaifa/im-task-sop/page`, params })
  },

  // 查询自动开发SOP详情
  getImTaskSop: async (id: number) => {
    return await request.get({ url: `/kaifa/im-task-sop/get?id=` + id })
  },

  // 新增自动开发SOP
  createImTaskSop: async (data: ImTaskSopVO) => {
    return await request.post({ url: `/kaifa/im-task-sop/create`, data })
  },

  // 修改自动开发SOP
  updateImTaskSop: async (data: ImTaskSopVO) => {
    return await request.put({ url: `/kaifa/im-task-sop/update`, data })
  },

  // 删除自动开发SOP
  deleteImTaskSop: async (id: number) => {
    return await request.delete({ url: `/kaifa/im-task-sop/delete?id=` + id })
  },

  // 导出自动开发SOP Excel
  exportImTaskSop: async (params) => {
    return await request.download({ url: `/kaifa/im-task-sop/export-excel`, params })
  },
}