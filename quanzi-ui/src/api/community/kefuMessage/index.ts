import request from '@/config/axios'
import {UnwrapRef} from "vue";

export interface ConversationVO {
  id: number
  userId: string
  userNickname: string
  userAvatar: string
  topicId: number
  lastMessageContent: string
  lastMessageContentType: number
  lastMessageTime: number
  createTime: number
}

export interface MessageVO {
  id: number
  conversationId: number
  senderType: string
  senderId: string
  senderNickname: string
  senderAvatar: string
  receiverId: string
  contentType: number
  content: string
  createTime: number
  readStatus: number
}

export interface MessageQuery {
  pageNo: number
  pageSize: number
  conversationId: number
  receiverId: number
}

// 获取圈子会话列表
export const getTopicConversationList = (topicId: number, nickName: String): Promise<ConversationVO[]> => {
  return request.get({
    url: '/promotion/kefu-message/getTopicConversationList',
    params: { topicId, nickName }
  })
}

// 获取私信消息列表
export const getTopicMessage = (params: MessageQuery) => {
  return request.get({
    url: '/promotion/kefu-message/getTopicMessage',
    params
  })
}

// 导出API对象
export const KefuMessageApi = {
  getTopicConversationList,
  getTopicMessage
}
