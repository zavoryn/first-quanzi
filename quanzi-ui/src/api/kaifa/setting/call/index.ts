import request from '@/config/axios'

// 打招呼配置 VO
export interface ImCallConfigVO {
  id: number // id
  content: string // 发送内容
  type: string // 消息类型 text文本 image图片 file文件，mini小程序 ( 废弃：0:文字 1:图片 2:文件 3:语音 4:视频 21:提示)
  corpId: number // 所属企业
  status: string // 是否启用
}

// 打招呼配置 API
export const ImCallConfigApi = {
  // 查询打招呼配置分页
  getImCallConfigPage: async (params: any) => {
    return await request.get({ url: `/kaifa/im-call-config/page`, params })
  },

  // 查询打招呼配置详情
  getImCallConfig: async (id: number) => {
    return await request.get({ url: `/kaifa/im-call-config/get?id=` + id })
  },

  // 新增打招呼配置
  createImCallConfig: async (data: ImCallConfigVO) => {
    return await request.post({ url: `/kaifa/im-call-config/create`, data })
  },

  // 修改打招呼配置
  updateImCallConfig: async (data: ImCallConfigVO) => {
    return await request.put({ url: `/kaifa/im-call-config/update`, data })
  },

  // 删除打招呼配置
  deleteImCallConfig: async (id: number) => {
    return await request.delete({ url: `/kaifa/im-call-config/delete?id=` + id })
  },

  // 导出打招呼配置 Excel
  exportImCallConfig: async (params) => {
    return await request.download({ url: `/kaifa/im-call-config/export-excel`, params })
  },
}
