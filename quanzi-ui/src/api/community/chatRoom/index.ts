// src/api/community/chatRoom.ts
import request from '@/config/axios'

export interface ChatMessage {
  id: number
  conversationId: number
  senderId: number
  senderAvatar: string
  senderType: number
  receiverId: number
  contentType: number
  content: string
  readStatus: boolean
  createTime: number
  senderName: string
}

export const ChatRoomApi = {
  /** 获取话题组会话ID */
  getTopicGroupConversionId: async (topicId: number) => {
    return await request.get({ 
      url: '/promotion/kefu-message/getTopicGroupConversionId', 
      params: { topicId } 
    })
  },
  
  /** 获取话题组消息 */
  getTopicMessage: async (params: {
    conversationId: number
    pageNo: number
    pageSize: number
  }) => {
    return await request.get({ 
      url: '/promotion/kefu-message/getTopicMessage', 
      params 
    })
  }
}