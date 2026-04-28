import request from '@/config/axios'

// 查询新闻信息列表  列表
export const listNews = async (params) => {
  return await request.get({ url: '/live/sns-news/page', params })
}

// 查询新闻配置信息列表
export const newsPzlist = async (params) => {
  return await request.get({ url: '/live/sns-news/pzlist', params })
}

// 新闻状态修改  状态修改
export const changeNewsFlag = async (data) => {
  return await request.put({ url: '/live/sns-news/changeNewsFlag', data })
}

// 新增动态配置信息
export const newsPzAdd = async (data) => {
  return await request.post({ url: '/live/sns-news/newsPzAdd', data })
}

// 删除动态配置信息
export const newsPzDelete = async (id) => {
  return await request.delete({ url: '/live/sns-news/newsPzDelete/' + id })
}

// 查询动态配置信息
export const getNewsPz = async (id) => {
  return await request.get({ url: '/live/sns-news/getNewsPz/' + id })
}

// 修改动态配置信息
export const newsPzUpdate = async (data) => {
  return await request.put({ url: '/live/sns-news/newsPzUpdate', data })
}

// 更新新闻内容   内容修改
export const updateNewsContent = async (data) => {
  return await request.put({ url: '/live/sns-news/updateNewsContent', data })
}

// 查询新闻信息详细    详情
export const getNews = async (newsId) => {
  return await request.get({ url: '/live/sns-news/get?newsId=' + newsId })
}

// 新增新闻信息  新增
export const addNews = async (data) => {
  return await request.post({ url: '/live/sns-news/create', data })
}

// 修改新闻信息  修改
export const updateNews = async (data) => {
  return await request.put({ url: '/live/sns-news/update', data })
}

// 删除新闻信息 删除
export const delNews = async (newsId) => {
  return await request.delete({ url: '/live/sns-news/delete?id=' + newsId })
}

// 导出新闻信息
export const exportNews = async (params) => {
  return await request.get({ url: '/live/sns-news/export', params })
}
// 获取回显图片
export const getAblum = async (id) => {
  return await request.get({ url: '/live/sns-ablum/' + id })
}