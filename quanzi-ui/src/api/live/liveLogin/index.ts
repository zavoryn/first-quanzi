import request from '@/config/axios'

export interface ZbVO {
  cookie: string
}

// 直播登录
export const zbLogin = async () => {
  return await request.post({ url: '/system/auth/zb-login' })
}

// 检查直播登录
export const verifyCookie = async (data: ZbVO) => {
  return await request.post({ url: '/system/auth/verifyCookie', data })
}
