package cn.metast.tuoke.module.live.service.snsAct;

import cn.metast.tuoke.framework.security.core.LoginUser;
import cn.metast.tuoke.framework.security.core.util.SecurityFrameworkUtils;
import cn.metast.tuoke.module.live.controller.admin.snsActinfocfg.vo.SnsActInfoCfgPageReqVO;
import cn.metast.tuoke.module.live.controller.admin.snsActinfocfg.vo.SnsActInfoCfgRespVO;
import cn.metast.tuoke.module.live.controller.admin.snsActplayeruser.vo.SnsActPlayerUserRespVO;
import cn.metast.tuoke.module.live.controller.admin.snsActuser.vo.SnsActUserRespVO;
import cn.metast.tuoke.module.live.controller.admin.snsPost.vo.SnsPostRespVO;
import cn.metast.tuoke.module.live.controller.admin.snsPost.vo.SnsPostSaveReqVO;
import cn.metast.tuoke.module.live.controller.admin.vo.ActListDTO;
import cn.metast.tuoke.module.live.controller.admin.vo.ActListVo;
import cn.metast.tuoke.module.live.controller.admin.vo.ActPostInfo;
import cn.metast.tuoke.module.live.dal.dataobject.snsActinfocfg.SnsActInfoCfgDO;
import cn.metast.tuoke.module.live.dal.dataobject.snsComment.SnsCommentDO;
import cn.metast.tuoke.module.live.dal.mysql.snsAblum.SnsAblumMapper;
import cn.metast.tuoke.module.live.dal.mysql.snsActfile.SnsActFileMapper;
import cn.metast.tuoke.module.live.dal.mysql.snsActguest.SnsActGuestMapper;
import cn.metast.tuoke.module.live.dal.mysql.snsActnote.SnsActNoteMapper;
import cn.metast.tuoke.module.live.dal.mysql.snsActnotice.SnsActNoticeMapper;
import cn.metast.tuoke.module.live.dal.mysql.snsActschedule.SnsActScheduleMapper;
import cn.metast.tuoke.module.live.dal.mysql.snsActuser.SnsActUserMapper;
import cn.metast.tuoke.module.live.dal.mysql.snsComment.SnsCommentMapper;
import cn.metast.tuoke.module.live.dal.mysql.snsCommentlike.SnsCommentLikeMapper;
import cn.metast.tuoke.module.live.dal.mysql.snsCommentreply.SnsCommentReplyMapper;
import cn.metast.tuoke.module.live.dal.mysql.snsPost.SnsPostMapper;
import cn.metast.tuoke.module.live.service.snsActinfocfg.SnsActInfoCfgService;
import cn.metast.tuoke.module.live.service.snsActplayeruser.SnsActPlayerUserService;
import cn.metast.tuoke.module.live.service.snsActuser.SnsActUserService;
import cn.metast.tuoke.module.live.utils.BeanMapUtils;
import cn.metast.tuoke.module.live.utils.DateUtils;
import cn.metast.tuoke.module.live.utils.JsonUtil;
import cn.metast.tuoke.module.live.utils.PinyinUtils;
import cn.metast.tuoke.module.member.api.user.MemberUserApi;
import cn.metast.tuoke.module.member.api.user.dto.MemberUserRespDTO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.micrometer.common.util.StringUtils;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import cn.metast.tuoke.module.live.controller.admin.snsAct.vo.*;
import cn.metast.tuoke.module.live.dal.dataobject.snsAct.SnsActDO;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.pojo.PageParam;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;

import cn.metast.tuoke.module.live.dal.mysql.snsAct.SnsActMapper;

import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.framework.common.pojo.CommonResult.success;
import static cn.metast.tuoke.module.live.enums.ErrorCodeConstants.*;

/**
 * 活动详情 Service 实现类
 *
 * @author 夏兆金
 */
@Service
@Validated
public class SnsActServiceImpl implements SnsActService {

    @Resource
    private SnsActMapper snsActMapper;
    @Autowired
    private SnsPostMapper snsPostMapper;
    @Autowired
    private SnsActUserMapper snsActUserMapper;
    @Autowired
    private SnsCommentMapper commentMapper;
    @Autowired
    private SnsActFileMapper snsActFileMapper;
    @Autowired
    private SnsActGuestMapper snsActGuestMapper;
    @Autowired
    private SnsActNoticeMapper snsActNoticeMapper;
    @Autowired
    private SnsActScheduleMapper snsActScheduleMapper;
    @Autowired
    private SnsActPlayerUserService snsActPlayerUserService;
    @Autowired
    private SnsAblumMapper snsAblumMapper;
    @Autowired
    private SnsCommentLikeMapper commentLikeMapper;
    @Autowired
    private SnsActNoteMapper snsActNoteMapper;
    @Autowired
    private SnsActInfoCfgService snsActInfoCfgService;

    @Resource
    private MemberUserApi memberUserApi;

    @Autowired
    private SnsActUserService snsActUserService;
    @Autowired
    private SnsCommentReplyMapper commentReplyMapper;
    @Override
    public Long createSnsAct(SnsActSaveReqVO createReqVO) {
        // 插入
        SnsActDO snsAct = BeanUtils.toBean(createReqVO, SnsActDO.class);
        snsActMapper.insert(snsAct);
        // 返回
        return snsAct.getActId();
    }

    @Override
    public void updateSnsAct(SnsActSaveReqVO updateReqVO) {
        snsActMapper.updateSnsAct(updateReqVO);
    }

    @Override
    public void deleteSnsAct(Long id) {
        // 校验存在
        validateSnsActExists(id);
        // 删除
        snsActMapper.deleteById(id);
    }

    private void validateSnsActExists(Long id) {
        if (snsActMapper.selectSnsActByActId(id) == null) {
            throw exception(SNS_ACT_NOT_EXISTS);
        }
    }

    @Override
    public SnsActDO getSnsAct(Long id) {
        return snsActMapper.selectById(id);
    }

    @Override
    public PageResult<SnsActDO> getSnsActPage(SnsActPageReqVO pageReqVO) {
        return snsActMapper.selectPage(pageReqVO);
    }

    @Override
    public List<ActListVo> selectSnsActList(ActListDTO actListDTO) throws ParseException{
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat df_2=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf_2 = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
        SnsActPageReqVO snsAct = new SnsActPageReqVO();
        snsAct.setSpType(actListDTO.getSpType());
        snsAct.setLoginId(SecurityFrameworkUtils.getLoginUserId());
        if(actListDTO.getUserId()!=null){
            snsAct.setLoginId(actListDTO.getUserId());
        }
        if("owner".equals(actListDTO.getReqType()) || "join".equals(actListDTO.getReqType())){
            snsAct.setCreateUserId(String.valueOf(SecurityFrameworkUtils.getLoginUserId()));
        }
        snsAct.setReqType(actListDTO.getReqType());
        snsAct.setPostFlag(actListDTO.getPostFlag());
        if(actListDTO.getFlag()==null){//
            if(actListDTO.getMStatus()==null){
                snsAct.setMStatus(0);
            }else{
                snsAct.setMStatus(actListDTO.getMStatus());
            }
        }else{
            snsAct.setMStatus(actListDTO.getMStatus());
        }

        //pc端时间筛选项
        if(actListDTO.getParams()!=null) {
            snsAct.setParams(actListDTO.getParams());
        }
        snsAct.setPageNo(actListDTO.getPageNum());
        snsAct.setPageSize(actListDTO.getPageSize());
        List<SnsActDO> snsActList = selectSnsActListPage(snsAct).getList();
        List<ActListVo> vos = new ArrayList<>();
        String date= DateUtils.getTime();
        Date d1= null;
        try {
            d1 = df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(snsActList.size()>0){
            int i=0;
            for (SnsActDO act : snsActList){
                String et1=sdf.format(act.getEndDate());
                String et2=sdf1.format(act.getEndTime());
                String et=et1+" "+et2;
                Date d2=df.parse(et);
                if(StringUtils.isNotEmpty(actListDTO.getParam())){//1进行中0已结束
                    if("1".equals(actListDTO.getParam())){
                        if (d1.getTime() > d2.getTime()){
                            continue;
                        }
                    }else if("0".equals(actListDTO.getParam())){
                        if (d1.getTime() < d2.getTime()){
                            continue;
                        }
                    }
                }

                SnsPostSaveReqVO snsPost = snsPostMapper.selectSnsPostById(act.getPostId());
                ActListVo vo = new ActListVo();
                BeanMapUtils.copyBeanProp(vo,snsPost);
                vo.setCoverUrl(snsPost.getCoverUrl());
                vo.setActId(act.getActId());
                vo.setActType(act.getActType());
                vo.setActForm(act.getActForm());
                vo.setFee(act.getFee());


                ActListVo vp=getActNum(act.getActId());
                vo.setIsJoin(vp.getIsJoin());//判断是否参加
                vo.setBNum(vp.getBNum());//报名次数
                vo.setFstatus(vp.getFstatus());//是否缴费

                // 是否收藏
               /* if(actListDTO.getPostFlag()!=null&&actListDTO.getPostFlag()==1){
                    vo.setIsFavorite("Y");
                }else{
                    SnsPostFavorite postFavorite = new SnsPostFavorite();
                    postFavorite.setPostId(snsPost.getPostId());
                    postFavorite.setUserId(snsPost.getUserId());
                    List<SnsPostFavorite> snsPostFavorites2 = postFavoriteMapper.selectSnsPostFavoriteList(postFavorite);
                    if(snsPostFavorites2.size() > 0){
                        vo.setIsFavorite("Y");
                    }else {
                        vo.setIsFavorite("N");
                    }
                }*/

                vo.setMemberLimit(act.getMemberLimit());
                vo.setJoinLimit(act.getJoinLimit());
                vo.setFeeLimit(act.getFeeLimit());
                vo.setJoinMemberView(act.getJoinMemberView());
                vo.setBtnView(act.getBtnView());
                vo.setShareCfg(act.getShareCfg());
                vo.setAudtCfg(act.getAudtCfg());

                vo.setSort(act.getSort());
                vo.setQrcode(act.getQrcode());
                vo.setCheckFlag(act.getCheckFlag());

                //报名人数
                SnsActUserRespVO user=new SnsActUserRespVO();
                user.setActId(act.getActId());
                List<SnsActUserRespVO> userList = snsActUserMapper.selectSnsActUserAppList(user);
                vo.setSignNum(userList.size());
                //参与人数
                SnsActPlayerUserRespVO snsActPlayerUser=new SnsActPlayerUserRespVO();
                snsActPlayerUser.setActId(act.getActId());
                List<SnsActPlayerUserRespVO> ulist=snsActPlayerUserService.selectSnsActPlayerUserList(snsActPlayerUser);
                vo.setCanyuNum(ulist.size());



                vo.setLati(String.valueOf(act.getLati()));
                vo.setLongi(String.valueOf(act.getLongi()));
                vo.setLocation(act.getLocation());
                vo.setRegSetting(act.getRegSetting());
                vo.setMStatus(act.getMStatus());
                vo.setContactphone(act.getContactphone());

                vo.setStartDate(act.getStartDate()!=null?sdf_2.format(act.getStartDate()):null);
                vo.setEndDate(act.getEndDate()!=null?sdf_2.format(act.getEndDate()):null);
                vo.setStartTime(act.getStartTime()!=null?sdf1.format(act.getStartTime()):null);
                vo.setEndTime(act.getEndTime()!=null?sdf1.format(act.getEndTime()):null);

                //vo.setCreateTime(DateUtils.datetime(snsPost.getCreateTime()));
                vo.setAppyStartDate(act.getAppyStartDate()!=null?df_2.format(act.getAppyStartDate()):null);
                vo.setAppyEndDate(act.getAppyEndDate()!=null?df_2.format(act.getAppyEndDate()):null);

                vo.setSTime(sdf.format(act.getStartDate())+" "+sdf1.format(act.getStartTime()));
                vo.setETime(sdf.format(act.getEndDate())+" "+sdf1.format(act.getEndTime()));
                vo.setSpType(act.getSpType());
                /*SysUser user = userService.selectUserById(vo.getUserId());
                if(user != null) {
                    vo.setNickName(user.getNickName());
                    vo.setAvatar(CommUtils.getAvatarUrl(user.getAvatar()));
                }*/
                i++;
                vo.setNumber(i);
                vos.add(vo);
            }

        }
        return vos;
    }
    /**
     * 获取参数
     */
    public ActListVo getActNum(Long act){
        ActListVo vo = new ActListVo();
        LoginUser loginUser = SecurityFrameworkUtils.getLoginUser();
        Long uid = null;
        if(loginUser!=null){
            uid = loginUser.getId();
        }
        if(uid!=null){
            SnsActUserRespVO snsActUser=new SnsActUserRespVO();
            snsActUser.setActId(act);
            snsActUser.setUserId(uid);
            int count=snsActUserMapper.selectActUserCount(snsActUser);
            vo.setIsJoin(String.valueOf(count));//判断是否参加
            vo.setBNum(count);//报名次数

            snsActUser.setFeeTime(new Date());
            int counts=snsActUserMapper.selectActUserCount(snsActUser);
            if(counts==0){//是否缴费
                vo.setFstatus(0);
            }else{
                vo.setFstatus(1);
            }
        }
        return vo;
    }
    @Override
    public PageResult<SnsActDO> selectSnsActListPage(SnsActPageReqVO pageReqVO) {
        IPage<SnsActDO> page = new Page<>(pageReqVO.getPageNo(), pageReqVO.getPageSize());
        snsActMapper.selectSnsActList(page, pageReqVO);
        return new PageResult<>(page.getRecords(), page.getTotal());
    }

    @Override
    public int deleteSnsActAppById(Long id) {
        Long userId = SecurityFrameworkUtils.getLoginUserId();
        SnsActDO act = snsActMapper.selectSnsActById(id,userId);
        if(act!=null){
            //删除评论
            SnsCommentDO comment = new SnsCommentDO();
            comment.setPostId(act.getPostId());
            List<SnsCommentDO> snsComments = commentMapper.selectSnsCommentList(comment);
            if(!CollectionUtils.isEmpty(snsComments)) {
                for (SnsCommentDO c : snsComments) {
                    commentLikeMapper.deleteSnsCommentLikeByCommentid(c.getCommentId());
                    commentReplyMapper.deleteSnsCommentReplyByCommentid(c.getCommentId());
                }
            }
            commentMapper.deleteSnsCommentByPostId(act.getPostId());
            //删除相册,分享，收藏等等
            snsAblumMapper.deleteSnsAblumByPostId(act.getPostId());
            snsActMapper.deleteSnsActById(id);
            snsPostMapper.deleteSnsPostById(act.getPostId());
        }
        return  snsActUserMapper.deleteSnsActUserActById(id);
    }

    @Override
    public ActListVo selectSnsActById(Long id) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Long userId = SecurityFrameworkUtils.getLoginUserId();
        if(userId==null){
            userId=-1L;
        }
        SnsActDO act = snsActMapper.selectSnsActById(id,userId);
        //获得最新报名的前10条人员
        //如果是不显示就隐藏
        List<SnsActUserRespVO> userList=new ArrayList<SnsActUserRespVO>();
        if(act.getJoinMemberView()!=null&&act.getJoinMemberView()==0) {
            userList = snsActUserMapper.selectSnsActUserNewList(id);
            if (!CollectionUtils.isEmpty(userList)) {
                for (SnsActUserRespVO snsActUser : userList) {
                    snsActUser.setAvatar(snsActUser.getAvatar());
                }
            }
        }
        SnsPostSaveReqVO snsPost = snsPostMapper.selectSnsPostById(act.getPostId());
        snsPost.setCoverUrl(snsPost.getCoverUrl());
        ActListVo vo = new ActListVo();
        BeanMapUtils.copyBeanProp(vo,snsPost);
        vo.setCrowdQr(act.getCrowdQr());

        ActListVo vp=getActNum(act.getActId());
        vo.setIsJoin(vp.getIsJoin());//判断是否参加
        vo.setBNum(vp.getBNum());//报名次数
        vo.setFstatus(vp.getFstatus());//是否缴费


        //查新是否报名
        if(act.getRegisterTime()==null){
            vo.setRegisterType(0);
        }else{
            vo.setRegisterType(1);
        }
        //获得报名条件
        SnsActInfoCfgPageReqVO pageReqVO=new SnsActInfoCfgPageReqVO();
        pageReqVO.setActId(id);
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SnsActInfoCfgDO> infoCfg=snsActInfoCfgService.getSnsActInfoCfgPage(pageReqVO).getList();
        vo.setActInfoCfgs(infoCfg);//新增时返回字符串
        vo.setMemberLimit(act.getMemberLimit());
        vo.setJoinLimit(act.getJoinLimit());
        vo.setFeeLimit(act.getFeeLimit());
        vo.setJoinMemberView(act.getJoinMemberView());
        vo.setBtnView(act.getBtnView());
        vo.setShareCfg(act.getShareCfg());
        vo.setAudtCfg(act.getAudtCfg());
        //费用支出，1固定费用，2参与人*人数
        vo.setActPut(act.getActPut()+"");


        vo.setSort(act.getSort());
        vo.setQrcode(act.getQrcode());
        vo.setCheckFlag(act.getCheckFlag());
        Long uid = SecurityFrameworkUtils.getLoginUserId();
        if(uid==null){
            uid = -1L;
        }
        vo.setActUserList(userList);
        //vo.setCommentInfo(dynaService.getCommentInfo(act.getPostId(),uid, null));
        vo.setActId(act.getActId());
        vo.setActType(act.getActType());
        vo.setActForm(act.getActForm());
        vo.setFee(act.getFee());

        SnsActUserRespVO usern=new SnsActUserRespVO();
        usern.setActId(act.getActId());
        List<SnsActUserRespVO> users = snsActUserService.selectSnsActUserAppList(usern);
        vo.setSignNum(users.size());

        vo.setLati(String.valueOf(act.getLati()));
        vo.setLongi(String.valueOf(act.getLongi()));
        vo.setLocation(act.getLocation());
        vo.setRegSetting(act.getRegSetting());
        vo.setContactphone(act.getContactphone());
        vo.setStartDate(sdf.format(act.getStartDate()));
        vo.setEndDate(sdf.format(act.getEndDate()));
        vo.setStartTime(sdf1.format(act.getStartTime()));
        vo.setEndTime(sdf1.format(act.getEndTime()));


        vo.setAppyStartDate(act.getAppyStartDate()!=null?sdf2.format(act.getAppyStartDate()):null);
        vo.setAppyEndDate(act.getAppyEndDate()!=null?sdf2.format(act.getAppyEndDate()):null);
        MemberUserRespDTO user =memberUserApi.getUser(vo.getUserId());
        if(user != null) {
            vo.setNickName(user.getNickname());
            vo.setAvatar(user.getAvatar());
        }
        return vo;
    }

    @Override
    public SnsActDO selectSnsActByActId(Long id) {
        return snsActMapper.selectSnsActByActId(id);
    }

    @Override
    public int insertSnsActPost(ActPostInfo actPostInfo) throws ParseException {
        int result = 0;

        if(StringUtils.isNotBlank(actPostInfo.getStartDate()) && actPostInfo.getStartDate().indexOf("/") != -1){
            actPostInfo.setStartDate(actPostInfo.getStartDate().replace("/","-"));
        }
        if(StringUtils.isNotBlank(actPostInfo.getEndDate()) && actPostInfo.getEndDate().indexOf("/") != -1){
            actPostInfo.setEndDate(actPostInfo.getEndDate().replace("/","-"));
        }
        if(StringUtils.isNotBlank(actPostInfo.getAppyStartDate()) && actPostInfo.getAppyStartDate().indexOf("/") != -1){
            actPostInfo.setAppyStartDate(actPostInfo.getAppyStartDate().replace("/","-"));
        }
        if(StringUtils.isNotBlank(actPostInfo.getAppyEndDate()) && actPostInfo.getAppyEndDate().indexOf("/") != -1){
            actPostInfo.setAppyEndDate(actPostInfo.getAppyEndDate().replace("/","-"));
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:MM:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        //act 实体
        SnsActSaveReqVO actInfo = new SnsActSaveReqVO();
        actInfo.setActType(actPostInfo.getActType());
        actInfo.setActForm(actPostInfo.getActForm());
        actInfo.setFee(new BigDecimal(actPostInfo.getFee()!=null?actPostInfo.getFee():Double.parseDouble("0")));
        actInfo.setLati(new BigDecimal(Double.parseDouble(StringUtils.isNotBlank(actPostInfo.getLati())?actPostInfo.getLati():"0")));
        actInfo.setLongi(new BigDecimal(Double.parseDouble(StringUtils.isNotBlank(actPostInfo.getLongi())?actPostInfo.getLongi():"0")));
        actInfo.setLocation(actPostInfo.getLocation());
        actInfo.setStartDate(sdf.parse(actPostInfo.getStartDate()));
        actInfo.setStartTime(sdf1.parse(actPostInfo.getStartTime()));
        actInfo.setEndDate(sdf.parse(actPostInfo.getEndDate()));
        actInfo.setEndTime(sdf1.parse(actPostInfo.getEndTime()));
        actInfo.setRegSetting(actPostInfo.getRegSetting());
        actInfo.setContactphone(actPostInfo.getContactphone());

        //默认下线
        if(actPostInfo.getMStatus()!=null){
            actInfo.setMStatus(actPostInfo.getMStatus());
        }

        actInfo.setAppyStartDate(actPostInfo.getAppyStartDate()!=null?sdf2.parse(actPostInfo.getAppyStartDate()):null);
        actInfo.setAppyEndDate(actPostInfo.getAppyEndDate()!=null?sdf2.parse(actPostInfo.getAppyEndDate()):null);
        actInfo.setQrcode(actPostInfo.getQrcode());
        actInfo.setCheckFlag(actPostInfo.getCheckFlag());
        actInfo.setSort(actPostInfo.getSort());
        //新加的参数
        actInfo.setMemberLimit(actPostInfo.getMemberLimit());
        actInfo.setJoinLimit(actPostInfo.getJoinLimit());
        actInfo.setFeeLimit(actPostInfo.getFeeLimit());
        actInfo.setJoinMemberView(actPostInfo.getJoinMemberView());
        actInfo.setBtnView(actPostInfo.getBtnView());
        actInfo.setShareCfg(actPostInfo.getShareCfg());
        actInfo.setAudtCfg(actPostInfo.getAudtCfg());
        actInfo.setActPut(actPostInfo.getActPut());
        actInfo.setIsRecom(actPostInfo.getIsRecom());
        actInfo.setCrowdQr(actPostInfo.getCrowdQr());


        //post 实体
        //picIds,mediaId,fileId 未赋值
        SnsPostRespVO postInfo = new SnsPostRespVO();
        postInfo.setContent(actPostInfo.getContent());
        postInfo.setTitle(actPostInfo.getTitle());
        postInfo.setTag(actPostInfo.getTag());
        postInfo.setPicNum(actPostInfo.getPicNum());
        postInfo.setCommentFlag(actPostInfo.getCommentFlag());
        postInfo.setCoverUrl(actPostInfo.getCoverUrl());
        postInfo.setSpType(actPostInfo.getSpType());

        postInfo.setCreateTime(DateUtils.getNowDate());
        if(actPostInfo.getActId()!=null){
            //修改先查过去postId 修改post
            SnsActDO snsActById = snsActMapper.selectSnsActById(actPostInfo.getActId(),SecurityFrameworkUtils.getLoginUserId());
            postInfo.setPostId(snsActById.getPostId());
            int postResult = snsPostMapper.updateSnsPost(postInfo);
            if(postResult == 1){
                actInfo.setActId(actPostInfo.getActId());
                int actResult = snsActMapper.updateSnsAct(actInfo);
                if(actResult == 1){
                    result = actInfo.getActId().intValue();
                }
            }
            //计算报名模板
            cfgModel(actPostInfo.getActInfoCfgList(),actInfo.getActId());
        }else{
            //新增 先post 后act
            postInfo.setPostType("act");
            postInfo.setUserId(SecurityFrameworkUtils.getLoginUserId());
            actInfo.setSignNum(0);
            int postResult = snsPostMapper.insertSnsPost(postInfo);
            if(postResult == 1){
                /*SnsPostRespVO postInfoId = new SnsPostRespVO();
                postInfoId.setId(postInfo.getId());
                postInfoId.setPostId(postInfo.getId());
                snsPostMapper.updateSnsPostPc(postInfoId);*/

                actInfo.setPostId(postInfo.getPostId());
                actInfo.setCreateUserId(String.valueOf(SecurityFrameworkUtils.getLoginUserId()));

                //actInfo.setMstatus(actPostInfo.getMstatus());

                int actResult = snsActMapper.insertSnsAct(actInfo);
                if(actResult == 1){
                    result =actInfo.getActId().intValue();
                   /* SnsActDO snsActDO=new SnsActDO();
                    snsActDO.setId(actInfo.getId());
                    snsActDO.setActId(actInfo.getId());
                    snsActMapper.updateById(snsActDO);*/
                    //app新增
                    List<SnsActInfoCfgRespVO> actInfoCfg=actPostInfo.getSnsActInfoCfgs();
                    if(!CollectionUtils.isEmpty(actInfoCfg)){
                        for(SnsActInfoCfgRespVO ls:actInfoCfg){
                            ls.setActId(actInfo.getActId());
                        }
                        snsActInfoCfgService.insertSnsActUserInfoCfg(actInfoCfg);
                    }
                    //计算报名模板
                    cfgModel(actPostInfo.getActInfoCfgList(),actInfo.getActId());
                }
            }
        }

        return result;
    }

    @Override
    public SnsActDO selectSnsActByIdPc(Long id) {
        Long userId = SecurityFrameworkUtils.getLoginUserId();
        if(userId==null){
            userId=-1L;
        }
        SnsActDO act=snsActMapper.selectSnsActById(id,userId);
        if(act!=null) {
            act.setCoverUrl(act.getCoverUrl());
            //获得报名内容
            SnsActInfoCfgRespVO actInfo = new SnsActInfoCfgRespVO();
            actInfo.setActId(id);
            List<SnsActInfoCfgRespVO> infoCfgs = snsActInfoCfgService.selectSnsActInfoCfgList(actInfo);
            String actInfoCfg = "";
            if (!CollectionUtils.isEmpty(infoCfgs)) {
                for (SnsActInfoCfgRespVO snsActInfoCfg : infoCfgs) {
                    actInfoCfg += snsActInfoCfg.getFieldName() + "、";
                }
                actInfoCfg = actInfoCfg.substring(0, actInfoCfg.length() - 1);
                act.setActInfoCfg(actInfoCfg);
            }
        }
        return act;
    }
    public void cfgModel(String actInfoCfgList,Long actId){
        //pc端新增
        if(StringUtils.isNotEmpty(actInfoCfgList)){
            //先删除老的
            snsActInfoCfgService.deleteSnsActInfoCfgById(actId);
            List<Map<String,Object>> actInfoCfgpc= JsonUtil.jsonArray2List(actInfoCfgList);
            List<SnsActInfoCfgRespVO> cfgpc=new ArrayList<SnsActInfoCfgRespVO>();
            if(!CollectionUtils.isEmpty(actInfoCfgpc)){
                for(Map<String,Object> ls:actInfoCfgpc){
                    SnsActInfoCfgRespVO snsActInfoCfg=new SnsActInfoCfgRespVO();
                    snsActInfoCfg.setActId(actId);
                    snsActInfoCfg.setFieldName(ls.get("fieldName")!=null?(String)ls.get("fieldName"):"");
                    snsActInfoCfg.setFieldKey(PinyinUtils.getFullSpell((String)ls.get("fieldName")));
                    snsActInfoCfg.setFieldType(ls.get("fieldType")!=null?Integer.parseInt(ls.get("fieldType").toString()):null);
                    snsActInfoCfg.setFieldInput(ls.get("fieldInput")!=null?(String)ls.get("fieldInput"):"");
                    snsActInfoCfg.setFieldValue(ls.get("fieldValue")!=null?(String)ls.get("fieldValue"):"");
                    cfgpc.add(snsActInfoCfg);
                }
                snsActInfoCfgService.insertSnsActUserInfoCfg(cfgpc);
            }
        }
    }
}
