package cn.metast.tuoke.module.mp.service.mpTask;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.metast.tuoke.framework.common.exception.ServiceException;
import cn.metast.tuoke.framework.common.util.date.DateUtils;
import cn.metast.tuoke.framework.security.core.util.SecurityFrameworkUtils;
import cn.metast.tuoke.framework.tenant.core.context.TenantContextHolder;
import cn.metast.tuoke.framework.tenant.core.util.TenantUtils;
import cn.metast.tuoke.module.mp.controller.admin.dify.DifyMpClient;
import cn.metast.tuoke.module.mp.controller.admin.mpTaskrecord.vo.MpTaskRecordSaveReqVO;
import cn.metast.tuoke.module.mp.dal.dataobject.account.MpAccountDO;
import cn.metast.tuoke.module.mp.dal.dataobject.material.MpMaterialDO;
import cn.metast.tuoke.module.mp.dal.dataobject.mpTaskrecord.MpTaskRecordDO;
import cn.metast.tuoke.module.mp.dal.mysql.account.MpAccountMapper;
import cn.metast.tuoke.module.mp.framework.mp.core.MpServiceFactory;
import cn.metast.tuoke.module.mp.service.material.MpMaterialService;
import cn.metast.tuoke.module.mp.service.mpTaskrecord.MpTaskRecordService;
import cn.metast.tuoke.module.system.api.tenant.TenantApi;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.ttl.threadpool.TtlExecutors;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.draft.WxMpAddDraft;
import me.chanjar.weixin.mp.bean.draft.WxMpDraftArticles;
import me.chanjar.weixin.mp.bean.draft.WxMpUpdateDraft;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;

import cn.metast.tuoke.module.mp.controller.admin.mpTask.vo.*;
import cn.metast.tuoke.module.mp.dal.dataobject.mpTask.MpTaskDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;

import cn.metast.tuoke.module.mp.dal.mysql.mpTask.MpTaskMapper;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.framework.common.pojo.CommonResult.success;
import static cn.metast.tuoke.module.mp.enums.ErrorCodeConstants.*;

/**
 * 自动开发公众号信息 Service 实现类
 *
 * @author 苏丹家园
 */
@Slf4j
@Service
@Validated
public class MpTaskServiceImpl implements MpTaskService{

    @Resource
    private MpTaskMapper taskMapper;
    @Resource
    private MpAccountMapper mpAccountMapper;
    @Resource
    private DifyMpClient difyMpClient;
    @Resource
    private MpMaterialService mpMaterialService;
    @Resource
    private MpServiceFactory mpServiceFactory;
    @Resource
    private MpTaskRecordService mpTaskRecordService;
    @Resource
    private TenantApi tenantApi;

    private ExecutorService executor;

    private final  static ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1000);
    private static final ConcurrentHashMap<String, ScheduledFuture<?>> tasks = new ConcurrentHashMap<>();
    @Override
    public Long createTask(MpTaskSaveReqVO createReqVO) {
        // 插入
        MpTaskDO task = BeanUtils.toBean(createReqVO, MpTaskDO.class);
        taskMapper.insert(task);
        // 返回
        return task.getId();
    }
    @PostConstruct
    public void init() {
        executor = TtlExecutors.getTtlExecutorService(new ThreadPoolExecutor(
                500,
                1000,
                120L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(500),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy()
        ));
    }
    @Override
    public void updateTask(MpTaskSaveReqVO updateReqVO) {
        // 校验存在
        validateTaskExists(updateReqVO.getId());
        // 更新
        MpTaskDO updateObj = BeanUtils.toBean(updateReqVO, MpTaskDO.class);
        taskMapper.updateById(updateObj);
    }

    @Override
    public void deleteTask(Long id) {
        // 校验存在
        validateTaskExists(id);
        // 删除
        taskMapper.deleteById(id);
    }

    private void validateTaskExists(Long id) {
        if (taskMapper.selectById(id) == null) {
            throw exception(TASK_NOT_EXISTS);
        }
    }

    @Override
    public MpTaskDO getTask(Long id) {
        return taskMapper.selectById(id);
    }

    @Override
    public PageResult<MpTaskDO> getTaskPage(MpTaskPageReqVO pageReqVO) {
        return taskMapper.selectPage(pageReqVO);
    }

    @Override
    public Long saveTask(MpTaskSaveReqVO createReqVO) {
        //验证任务名称
        String taskName = createReqVO.getTaskName();
        List<MpTaskDO> imTaskDtos = taskMapper.selectListTaskName(taskName);
        if(CollectionUtils.isNotEmpty(imTaskDtos)){
            throw new ServiceException(500,"该任务名称已存在！");
        }
        if(StringUtils.isEmpty(createReqVO.getTaskId())){
            String taskId = createUniqueNo("TK");
            createReqVO.setTaskId(taskId);
            /*if(createReqVO.getIsRules()!=null){
                //规则  发送规则1草稿2审核0发送
                if(createReqVO.getIsRules()==0){
                    //立即发送
                    createReqVO.setStatus("0");
                }else if(createReqVO.getIsRules()==1){
                    //定时发送
                    createReqVO.setStatus("0");
                }else if(createReqVO.getIsRules()==2){
                    //每天发送
                    createReqVO.setStatus("0");
                }
            }*/
            createReqVO.setTemplateId(Long.parseLong(createReqVO.getTemplateValue()));
            createReqVO.setStatus("0");
            Long id=createTask(createReqVO);
            createReqVO.setId(id);
            List<MpTaskSaveReqVO> taskList = new ArrayList<MpTaskSaveReqVO>();
            taskList.add(createReqVO);
            createEmailTask(taskList);
            return id;
        }
        return null;
    }

    @Override
    public void createEmailTask(List<MpTaskSaveReqVO> taskList) {
        //1.获取所有自动化任务
        if(CollectionUtils.isEmpty(taskList)){
            MpTaskDO taskQuery = new MpTaskDO();
            taskQuery.setStatus("0");//正常运行中的任务
            taskQuery.setIsRules(0);//正常的发送
            List<MpTaskDO> list = selectTaskAll(taskQuery);
            taskList=BeanUtils.toBean(list, MpTaskSaveReqVO.class);
        }
        for (MpTaskSaveReqVO taskDto : taskList) {
            if (!"0".equals(taskDto.getStatus())) {
                continue;
            }
            String taskId = taskDto.getTaskId();
            if(taskDto.getIsTask()!=null){
                if(taskDto.getIsTask()==0){
                    executorService.submit(() -> {
                        try {
                            //立即发送
                            onceSend(taskDto);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }else if(taskDto.getIsTask()==1){
                    //定时发送
                    String sendTime = taskDto.getSendTime();//发送时间
                    String fromDuration = taskDto.getFromDuration(); //10:00
                    LocalDateTime now = LocalDateTime.now();
                    String dateTimeStr = sendTime + "T" + fromDuration + ":00";
                    LocalDateTime beginDate = LocalDateTime.parse(dateTimeStr);
                    // 任务已创建还没执行，计算现在到首次执行时间的时差
                    long initialDelay = 10;
                    if (now.isBefore(beginDate)) {
                        initialDelay = Duration.between(now, beginDate).getSeconds();
                    }
                    Runnable task = () -> {
                        log.info("Task started....");
                        //发送
                        onceSend(taskDto);
                    };
                    log.info("------initialDelay111111-----{}---{}", initialDelay);
                    // 计划任务在给定的延迟后开始
                    ScheduledFuture<?> future = executorService.schedule(task, initialDelay, TimeUnit.SECONDS);
                    tasks.put(taskDto.getTaskId(), future);
                }else if(taskDto.getIsTask()==2){
                    //每天发送
                    LocalDateTime now = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    String sendTime = formatter.format(now);
                    String fromDuration = taskDto.getFromDuration();//发送时间
                    String dateTimeStr = sendTime + "T" + fromDuration + ":00";
                    LocalDateTime beginDate = LocalDateTime.parse(dateTimeStr);
                    // 任务已创建还没执行，计算现在到首次执行时间的时差
                    long initialDelay = 10;
                    if (now.isBefore(beginDate)) {
                        initialDelay = Duration.between(now, beginDate).getSeconds();
                    }
                    Runnable task = () -> {
                        log.info("Task started....");
                        //发送
                        onceSend(taskDto);
                    };
                    log.info("------initialDelay22222222-----{}---{}", initialDelay);
                    // 计划任务在给定的延迟后开始
                    ScheduledFuture<?> future = executorService.schedule(task, initialDelay, TimeUnit.SECONDS);
                    tasks.put(taskDto.getTaskId(), future);
                }
            }
        }
    }
    public void onceSend(MpTaskSaveReqVO taskDto){
    try {
        List<MpTaskRecordDO> mpTaskRecordDOS = mpTaskRecordService.getTaskRecordList(taskDto);
        if (CollectionUtils.isEmpty(mpTaskRecordDOS)) {
            SimpleDateFormat df =new SimpleDateFormat("yyyy-MM-dd");
            //查询所有公众号
            List<MpAccountDO> accounts = mpAccountMapper.selectList();
            if (CollectionUtils.isNotEmpty(accounts)) {
                for (MpAccountDO account : accounts) {
                    //生成文案
                    JSONObject result = new JSONObject();
                    JSONObject params = new JSONObject();
                    //文案生成
                    params.put("response_mode", "blocking");
                    params.put("user", "qinmy@metast.cn");
                    params.put("files", "");
                    Map<String, Object> inputs = new HashMap<>();
                    inputs.put("type", "text");
                    inputs.put("platform", "wxgzh");
                    inputs.put("size", "500");
                    inputs.put("content", taskDto.getTitle());
                    params.put("inputs", inputs);
                    JSONObject objResult = difyMpClient.sendWxRequest(difyMpClient.getDeaultClient(), "POST", "/workflows/run", params, "app-TIGLwLfH2mILOLwIgxollM70");
                    if (ObjectUtil.isNotEmpty(objResult) && objResult.containsKey("data")) {
                        JSONObject data = objResult.getJSONObject("data");
                        if (ObjectUtil.isNotEmpty(data) && data.containsKey("outputs")) {
                            //处理order 数据，组装fifee
                            JSONObject outputs = data.getJSONObject("outputs");
                            JSONObject output = outputs.getJSONObject("output");
                            result.put("content", JSONObject.parseObject(output.getString("output")));
                        }
                    }
                    //图片生成
                    inputs = new HashMap<>();
                    inputs.put("type", "文生图片");
                    inputs.put("prompt", taskDto.getTitle());
                    params.put("inputs", inputs);
                    String mediaIdUrl = "";
                    String mediaUrl = "";
                    objResult = difyMpClient.sendWxRequest(difyMpClient.getDeaultClient(), "POST", "/workflows/run", params, "app-FniSZO0Y6qPpzJp90iuz8MTV");
                    if (ObjectUtil.isNotEmpty(objResult) && objResult.containsKey("data")) {
                        JSONObject data = objResult.getJSONObject("data");
                        if (ObjectUtil.isNotEmpty(data) && data.containsKey("outputs")) {
                            //处理order 数据，组装fifee
                            JSONObject outputs = data.getJSONObject("outputs");
                            JSONArray output = outputs.getJSONArray("output");
                            if (CollectionUtil.isNotEmpty(output)) {
                                JSONObject jsonObject = output.getJSONObject(0);
                                JSONArray image_urls = jsonObject.getJSONArray("image_urls");
                                //result.put("image",mpMaterialService.uploadPermanentMaterialURL(account.getId(),image_urls.getString(0)));
                                MpMaterialDO mpMaterialDO = mpMaterialService.uploadPermanentMaterialURL(account.getId(), image_urls.getString(0));
                                if (mpMaterialDO != null) {
                                    mediaIdUrl = mpMaterialDO.getMediaId();
                                    mediaUrl = mpMaterialDO.getUrl();
                                }
                            }
                        }
                    }
                    JSONObject str = result.getJSONObject("content");
                    String content = str.getString("content");
                    //发送公众号
                    WxMpService mpService = mpServiceFactory.getRequiredMpService(account.getId());
                    //赋值
                    List<WxMpDraftArticles> articles = new ArrayList<>();
                    WxMpDraftArticles article = new WxMpDraftArticles();
                    article.setTitle(taskDto.getTitle());
                    article.setThumbMediaId(mediaIdUrl);
                    article.setUrl(mediaUrl);
                    article.setContent(content);
                    articles.add(article);
                    WxMpAddDraft draft = new WxMpAddDraft();
                    draft.setArticles(articles);
                    String mediaId = mpService.getDraftService().addDraft(draft);
                    try {
                        String publishId = mpService.getFreePublishService().submit(mediaId);
                        if (StringUtils.isNotBlank(publishId)) {
                            //发送成功
                            MpTaskRecordSaveReqVO mpTaskRecordSaveReqVO = new MpTaskRecordSaveReqVO();
                            mpTaskRecordSaveReqVO.setTaskId(taskDto.getTaskId());
                            mpTaskRecordSaveReqVO.setContent(content);
                            mpTaskRecordSaveReqVO.setUrl(mediaId);
                            mpTaskRecordSaveReqVO.setSendTime(df.format(new Date()));
                            mpTaskRecordSaveReqVO.setReceiveUserId(account.getAccount());
                            mpTaskRecordSaveReqVO.setReceiveUserName(account.getName());
                            mpTaskRecordSaveReqVO.setStatus("1");
                            mpTaskRecordSaveReqVO.setSendUserId(publishId);//先放上面
                            mpTaskRecordService.createTaskRecord(mpTaskRecordSaveReqVO);
                            //发完后关闭任务
                            this.cancelTask(taskDto.getTaskId());
                        } else {
                            //发送失败
                            MpTaskRecordSaveReqVO mpTaskRecordSaveReqVO = new MpTaskRecordSaveReqVO();
                            mpTaskRecordSaveReqVO.setTaskId(taskDto.getTaskId());
                            mpTaskRecordSaveReqVO.setContent(content);
                            mpTaskRecordSaveReqVO.setUrl(mediaId);
                            mpTaskRecordSaveReqVO.setSendTime(df.format(new Date()));
                            mpTaskRecordSaveReqVO.setReceiveUserId(account.getAccount());
                            mpTaskRecordSaveReqVO.setReceiveUserName(account.getName());
                            mpTaskRecordSaveReqVO.setStatus("2");
                            mpTaskRecordService.createTaskRecord(mpTaskRecordSaveReqVO);
                            //发完后关闭任务
                            this.cancelTask(taskDto.getTaskId());
                        }
                    } catch (WxErrorException e) {
                        //发送失败
                        MpTaskRecordSaveReqVO mpTaskRecordSaveReqVO = new MpTaskRecordSaveReqVO();
                        mpTaskRecordSaveReqVO.setTaskId(taskDto.getTaskId());
                        mpTaskRecordSaveReqVO.setContent(content);
                        mpTaskRecordSaveReqVO.setUrl(mediaId);
                        mpTaskRecordSaveReqVO.setSendTime(df.format(new Date()));
                        mpTaskRecordSaveReqVO.setReceiveUserId(account.getAccount());
                        mpTaskRecordSaveReqVO.setReceiveUserName(account.getName());
                        mpTaskRecordSaveReqVO.setStatus("2");
                        mpTaskRecordService.createTaskRecord(mpTaskRecordSaveReqVO);
                        //发完后关闭任务
                        this.cancelTask(taskDto.getTaskId());
                        throw exception(FREE_PUBLISH_SUBMIT_FAIL, e.getError().getErrorMsg());
                    }
                }
            }
          }
        } catch(Exception e){

        }
         if(!"2".equals(taskDto.getIsTask())){
            //不是每天发送的，就完成
             taskDto.setStatus("1");
             updateTask(taskDto);
         }
    }
    @Override
    public List<MpTaskDO> selectTaskAll(MpTaskDO mpTaskRespVO) {
        return taskMapper.selectTaskAll(mpTaskRespVO);
    }

    @Override
    public void cancelTask(String taskId) {
        // 将执行计划存到Redis
        MpTaskDO taskQuery = new MpTaskDO();
        taskQuery.setTaskId(taskId);
        List<MpTaskDO> sopDtoList = selectTaskAll(taskQuery);
        if(CollectionUtils.isNotEmpty(sopDtoList)) {
            for(MpTaskDO sopDto : sopDtoList) {
                ScheduledFuture<?> scheduledFuture = tasks.get(sopDto.getTaskId());
                if (Objects.nonNull(scheduledFuture)) {
                    scheduledFuture.cancel(true);
                    boolean cancelled = scheduledFuture.isCancelled();
                    if (cancelled) {
                        log.info("task:" + sopDto.getTaskId() + "关闭成功");
                        tasks.remove(sopDto.getTaskId());
                    }
                }
            }
        }
    }

    @Override
    public void createMpTask_tnt() {
        List<Long> maps = tenantApi.getTenantIdList();
        for (Long id : maps) {
            TenantUtils.execute(id, () -> {
                TenantContextHolder.setTenantId(id);
                // 执行任务
                createEmailTask(null);
            });
        }
    }


    public static String createUniqueNo(String prefix){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateFormat = sdf.format(new Date());
        return prefix  + dateFormat + String.format("%04d",new Random().nextInt(9999));
    }
}
