import request from '@/config/axios'

// 公众号模板 VO
export interface TaskTemplateVO {
  id: number // 系统主键
  url: string // 模板url
  content: string // 模板内容
}

// 公众号模板 API
export const TaskTemplateApi = {
  // 查询公众号模板分页
  getTaskTemplatePage: async (params: any) => {
    return await request.get({ url: `/mp/task-template/page`, params })
  },

  // 查询公众号模板详情
  getTaskTemplate: async (id: number) => {
    return await request.get({ url: `/mp/task-template/get?id=` + id })
  },

  // 新增公众号模板
  createTaskTemplate: async (data: TaskTemplateVO) => {
    return await request.post({ url: `/mp/task-template/create`, data })
  },

  // 修改公众号模板
  updateTaskTemplate: async (data: TaskTemplateVO) => {
    return await request.put({ url: `/mp/task-template/update`, data })
  },

  // 删除公众号模板
  deleteTaskTemplate: async (id: number) => {
    return await request.delete({ url: `/mp/task-template/delete?id=` + id })
  },

  // 导出公众号模板 Excel
  exportTaskTemplate: async (params) => {
    return await request.download({ url: `/mp/task-template/export-excel`, params })
  },
}