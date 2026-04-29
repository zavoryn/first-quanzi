import request from '@/config/axios'

// TODO @dhb52：vo 缺少

// 删除优惠劵
export const deleteCoupon = async (id: number) => {
  return request.delete({
    url: `/promotion/coupon/delete?id=${id}`
  })
}

// 获得优惠劵分页
export const getCouponPage = async (params: PageParam) => {
  return request.get({
    url: '/promotion/coupon/page',
    params: params
  })
}
//同步优惠卷用户
export const syncCouponUserRecord= () => {
  return request.get({ url: '/promotion/coupon/syncCouponUserRecord' })
}

// 发送优惠券
export const sendCoupon = async (data: any) => {
  return request.post({
    url: '/promotion/coupon/send',
    data: data
  })
}
