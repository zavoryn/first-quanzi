import request from '@/config/axios'

// 下发记录 VO
export interface ExExecuteRecordVO {
  id: number // 系统主键
  sysUserId: number // 系统用户
  corpId: number // 平台Id
  corpName: string // 平台名称
  userName: string // 用户Id
  nickName: string // 用户昵称
  uuid: string // 设备Id
  beType: string // 下发类型
  beParam: string // 下发参数
  beTime: Date // 下发时间
  beStatus: string // 下发状态
  content: string // 下发内容
}

// 下发记录 API
export const ExExecuteRecordApi = {
  // 查询下发记录分页
  getExExecuteRecordPage: async (params: any) => {
    return await request.get({ url: `/kaifa/ex-execute-record/page`, params })
  },

  // 查询下发记录详情
  getExExecuteRecord: async (id: number) => {
    return await request.get({ url: `/kaifa/ex-execute-record/get?id=` + id })
  },

  // 新增下发记录
  createExExecuteRecord: async (data: ExExecuteRecordVO) => {
    return await request.post({ url: `/kaifa/ex-execute-record/create`, data })
  },

  // 修改下发记录
  updateExExecuteRecord: async (data: ExExecuteRecordVO) => {
    return await request.put({ url: `/kaifa/ex-execute-record/update`, data })
  },

  // 删除下发记录
  deleteExExecuteRecord: async (id: number) => {
    return await request.delete({ url: `/kaifa/ex-execute-record/delete?id=` + id })
  },

  // 导出下发记录 Excel
  exportExExecuteRecord: async (params) => {
    return await request.download({ url: `/kaifa/ex-execute-record/export-excel`, params })
  },
}
