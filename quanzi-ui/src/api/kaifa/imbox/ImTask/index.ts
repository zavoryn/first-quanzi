import request from '@/config/axios'

// 自动开发发件信息 VO
export interface ImTaskVO {
  id: number // 系统主键
  taskId: string // 任务id
  taskName: string // 任务名称
  sender: string // 发送人昵称
  sendUserId: number // 发送人
  chatType: number // 群发任务的类，1：表示发送给客户，2：表示发送给客户群
  isTask: number // 是否定时任务 0 立即发送 1 定时发送
  sendTime: string // 发送时间
  tag: string // 关键词标签
  remark: string // 备注
  draftId: string // 草稿Id
  status: string // 状态0进行中1结束任务2任务暂停
  delFlag: boolean // 删除标识
  createBy: number // 创建者
  createType: string // 创建类型
  sendLimit: number // 单日发送上限
  sendingDate: string // 发送时间(工作日、全周)
  fromDuration: string // 开始发送时间
  endDuration: string // 结束发送时间
  loopStatus: string // 循环状态
  loopNums: number // 循环间隔数
  loopUnit: string // 循环单位(天、周、月、年)
  round: number // 周期数
}

// 自动开发发件信息 API
export const ImTaskApi = {
  // 查询自动开发发件信息分页
  getImTaskPage: async (params: any) => {
    return await request.get({ url: `/kaifa/im-task/page`, params })
  },

  // 查询自动开发发件信息详情
  getImTask: async (id: number) => {
    return await request.get({ url: `/kaifa/im-task/get?id=` + id })
  },

  // 新增自动开发发件信息
  createImTask: async (data: ImTaskVO) => {
    return await request.post({ url: `/kaifa/im-task/create`, data })
  },

  // 修改自动开发发件信息
  updateImTask: async (data: ImTaskVO) => {
    return await request.put({ url: `/kaifa/im-task/update`, data })
  },

  // 删除自动开发发件信息
  deleteImTask: async (id: number) => {
    return await request.delete({ url: `/kaifa/im-task/delete?id=` + id })
  },

  // 导出自动开发发件信息 Excel
  exportImTask: async (params) => {
    return await request.download({ url: `/kaifa/im-task/export-excel`, params })
  },
}