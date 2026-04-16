import request from '@/config/axios'

// 对话分析 VO
export interface DialogAnalysisVO {
  id: number
  topicId: number
  userName1: string
  userName2: string
  dialogContent: string
  originalHtml: string
  audioUrls: string
  analysisResult: string
  status: number
  errorMsg: string
  createTime?: Date
}

// 对话分析 API
export const DialogAnalysisApi = {
  // 查询对话分析分页
  getDialogAnalysisPage: async (params: any) => {
    return await request.get({ url: `/community/dialog-analysis/page`, params })
  },

  // 查询对话分析详情
  getDialogAnalysis: async (id: number) => {
    return await request.get({ url: `/community/dialog-analysis/get?id=` + id })
  },

  // 新增对话分析
  createDialogAnalysis: async (data: DialogAnalysisVO) => {
    return await request.post({ url: `/community/dialog-analysis/create`, data })
  },

  // 修改对话分析
  updateDialogAnalysis: async (data: DialogAnalysisVO) => {
    return await request.put({ url: `/community/dialog-analysis/update`, data })
  },

  // 删除对话分析
  deleteDialogAnalysis: async (id: number) => {
    return await request.delete({ url: `/community/dialog-analysis/delete?id=` + id })
  },

  // 导出对话分析 Excel
  exportDialogAnalysis: async (params) => {
    return await request.download({ url: `/community/dialog-analysis/export-excel`, params })
  },

  // 导入HTML聊天记录并分析
  importAndAnalyze: async (topicId: number, htmlFile: File, audioFiles?: File[]) => {
    const formData = new FormData()
    formData.append('topicId', topicId.toString())
    formData.append('htmlFile', htmlFile)
    if (audioFiles && audioFiles.length > 0) {
      audioFiles.forEach((file) => {
        formData.append('audioFiles', file)
      })
    }
    return await request.post({
      url: `/community/dialog-analysis/import`,
      data: formData,
      headers: { 'Content-Type': 'multipart/form-data' }
    })
  },

  // 重新分析
  retryAnalyze: async (id: number) => {
    return await request.post({ url: `/community/dialog-analysis/retry?id=` + id })
  }
}

// 获取圈子简单列表（复用已有接口）
export const getTopicSimpleList = async () => {
  return await request.get({ url: `/community/cm-topic-member/topic-simple-list` })
}