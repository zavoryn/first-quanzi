import request from '@/config/axios'

// 用户账号授权 VO
export interface ImCorpUserAuthorityVO {
  id: number // 系统主键
  sysUserId: number // 授权用户
  userName: number // 企微账号
}

// 用户账号授权 API
export const ImCorpUserAuthorityApi = {
  // 查询用户账号授权分页
  getImCorpUserAuthorityPage: async (params: any) => {
    return await request.get({ url: `/kaifa/im-corp-user-authority/page`, params })
  },

  // 查询用户账号授权详情
  getImCorpUserAuthority: async (id: number) => {
    return await request.get({ url: `/kaifa/im-corp-user-authority/get?id=` + id })
  },

  // 新增用户账号授权
  createImCorpUserAuthority: async (data: ImCorpUserAuthorityVO) => {
    return await request.post({ url: `/kaifa/im-corp-user-authority/create`, data })
  },

  // 修改用户账号授权
  updateImCorpUserAuthority: async (data: ImCorpUserAuthorityVO) => {
    return await request.put({ url: `/kaifa/im-corp-user-authority/update`, data })
  },

  // 删除用户账号授权
  deleteImCorpUserAuthority: async (id: number) => {
    return await request.delete({ url: `/kaifa/im-corp-user-authority/delete?id=` + id })
  },

  // 导出用户账号授权 Excel
  exportImCorpUserAuthority: async (params) => {
    return await request.download({ url: `/kaifa/im-corp-user-authority/export-excel`, params })
  },
}