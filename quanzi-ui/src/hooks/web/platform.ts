
import { ExPlatformApi,ExPlatformVO } from "@/api/kaifa/ex/platform"
export default function(){

  const queryParams = reactive({
    pageNo: 1,
    pageSize: 100,
    code: undefined,
    name: undefined,
    logo: undefined,
    valid: undefined,
    status: undefined,
    sort: undefined,
    remark: undefined,
    deleted: undefined,
    creator: undefined,
    createTime: [],
    updater: undefined,
    isDefault: undefined,
    number:undefined,
    authorize:undefined
  })

  const platforms = ref<ExPlatformVO[]>([]) // 平台列表
  /** 查询列表 */
  const getPlatformList = async () => {
    try {
      const data = await ExPlatformApi.getExPlatformPage(queryParams)
      platforms.value = data.list
    } finally {
    }
  }
  onMounted(() => {
    getPlatformList()
  })
  return { platforms, getPlatformList };
}