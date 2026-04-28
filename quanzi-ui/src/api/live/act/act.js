import request from '@/config/axios'

// 查询活动详情列表  列表
export const listAct = async (params) => {
  return await request.get({ url: '/live/sns-act/list', params })
}

// 获取活动统计总数
export const getTotal = async (params) => {
  return await request.get({ url: '/live/sns-act/getTotal', params })
}

// 查询活动详情详细  详情 
export const getAct = async (id) => {
  return await request.get({ url: '/live/sns-act/getAcInfo/' + id })
}

// 新增活动详情
export const addAct = async (data) => {
  return await request.post({ url: '/live/sns-act/addAct', data })
}

// 修改活动详情
export const updateAct = async (data) => {
  return await request.put({ url: '/live/sns-act', data })
}

// 修改活动详情信息  修改删除
export const updateActInfo = async (data) => {
  return await request.post({ url: '/live/sns-act/updateActInfo', data })
}

// 活动状态修改   状态
export const changeActFlag = async (actId, msFlag) => {
  const data = { actId, msFlag }
  return await request.put({ url: '/live/sns-act/changeActFlag', data })
}

// 删除活动详情  删除
export const delAct = async (id) => {
  return await request.delete({ url: '/live/sns-act/delete?id=' + id })
}

// 导出活动详情
export const exportAct = async (params) => {
  return await request.get({ url: '/live/sns-act/export', params })
}

// 选择模版
export const getTemplate = async (params) => {
  return await request.get({ url: '/live/sns-act-info-template/page', params })
}