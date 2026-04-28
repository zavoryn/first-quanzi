import request from '@/config/axios'

// 任务发送记录 VO
export interface ImMessageRecordVO {
  id: number // 系统主键
  count: number // 任务总人数
  content: string // 任务内容
  source: string // 任务来源(系统组、群发消息)
  gap: number // 任务间隔
  status: string // 任务状态
}

// 任务发送记录 API
export const ImMessageRecordApi = {
  // 查询任务发送记录分页
  getImMessageRecordPage: async (params: any) => {
    return await request.get({ url: `/kaifa/im-message-record/page`, params })
  },
  // 查询任务发送记录分页
  getImMessageRecordList: async (params: any) => {
    return await request.get({ url: `/kaifa/im-message-record/list`, params })
  }, 

  // 查询任务发送记录详情
  getImMessageRecord: async (id: number) => {
    return await request.get({ url: `/kaifa/im-message-record/get?id=` + id })
  },

  // 新增任务发送记录
  createImMessageRecord: async (data: ImMessageRecordVO) => {
    return await request.post({ url: `/kaifa/im-message-record/create`, data })
  },

  // 修改任务发送记录
  updateImMessageRecord: async (data: ImMessageRecordVO) => {
    return await request.put({ url: `/kaifa/im-message-record/update`, data })
  },

  // 删除任务发送记录
  deleteImMessageRecord: async (id: number) => {
    return await request.delete({ url: `/kaifa/im-message-record/delete?id=` + id })
  },

  // 导出任务发送记录 Excel
  exportImMessageRecord: async (params) => {
    return await request.download({ url: `/kaifa/im-message-record/export-excel`, params })
  },

  optImRecordApi: async (data: ImMessageRecordVO) => {
    return await request.put({ url: `/kaifa/im-message-record/optTask`, data })
  },
  
  listMessageApi: async (params: any) => {
    return await request.get({ url: `/kaifa/im-message-record/messagelist`, params })
  },  
  
  findByName: async (name: string) => {
    return await request.get({ url: `/kaifa/im-message-record/findByName?name=` + name })
  },  

}
