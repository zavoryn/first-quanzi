// api/community/cmPost.ts
import request from '@/config/axios'

export interface CmPostVO {
  id: number
  topicId: number
  userId: number
  content: string
  pics?: string[]
  status: number
  postTop: number
  isLiked: boolean
  likeCount: number
  commentCount: number
  viewCount: number
  collectCount: number
  nickname: string
  avatar?: string
  createTime: Date
  updateTime: Date
}

export interface PostQueryParams {
  pageNo: number
  pageSize: number
  topicId: number
  keyWord?: string
  status?: string
  postTop?: number
}

export interface CommentVO {
  id: number
  postId: number
  userId: number
  content: string
  parentId: number
  likeCount: number
  isLiked: boolean
  nickname: string
  avatar?: string
  createTime: Date
  replies?: CommentVO[]
}

// 获取帖子分页
export const getCmPostPage = (params: PostQueryParams) => {
  return request.get({
    url: '/community/cm-post/page',
    params
  })
}

// 点赞帖子
export const likePost = (id: number) => {
  return request.post({
    url: `/community/cm-post/like`,
    params: { id }
  })
}

// 取消点赞
export const unlikePost = (id: number) => {
  return request.post({
    url: `/community/cm-post/unlike`,
    params: { id }
  })
}

// 置顶帖子
export const updatePostTop = (id: number, postTop: number) => {
  return request.put({
    url: `/community/cm-post/update`,
    data: { id, postTop }
  })
}

// 删除帖子
export const deletePost = (id: number) => {
  return request.delete({
    url: `/community/cm-post/delete`,
    params: { id }
  })
}