import request from '@/config/axios'

// 查询活动报名人员列表 列表
export const listUser = async (params) => {
  return await request.get({ url: '/live/sns-act-user/page',params })
}

// 查询活动报名人员详细 更多信息
export const getUser = async (params) => {
  return await request.get({ url: '/live/sns-act-info-user/page',params})
}

// 新增活动报名人员
export const addUser = async (data) => {
  return await request.post({ url: '/live/sns-act-user', data })
}

// 修改活动报名人员
export const updateUser = async (data) => {
  return await request.put({ url: '/live/sns-act-user', data })
}

// 删除活动报名人员 删除
export const delUser = async (id) => {
  return await request.delete({ url: '/live/sns-act-user/delete?id=' + id })
}

// 导出活动报名人员
export const exportUser = async (query) => {
  return await request.get({ url: '/live/sns-act-user/export', params: query })
}