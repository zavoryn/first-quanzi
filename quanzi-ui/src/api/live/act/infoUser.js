import request from '@/config/axios'

// 查询活动报名人员报名填写用户信息
export const listInfoUser = async (params) => {
  return await request.get({ url: '/system/actInfoUser/list',params })
}
