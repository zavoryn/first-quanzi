import request from '@/config/axios'

// 获取圈子简单列表
export const getTopicSimpleList = () => {
  return request.get({ url: '/community/cm-topic-member/topic-simple-list' })
}

// 添加免费会员
export const addFreeMember = (data: {
  topicId: number
  userId: number
  userName: string
  type: number
}) => {
  return request.post({ url: '/community/cm-topic-member/add-free-member', data })
}