package cn.metast.tuoke.module.kaifa.service.email.settingemail;

import cn.hutool.core.util.ObjectUtil;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.module.kaifa.dal.dataobject.email.settingemail.SettingEmailDO;
import cn.metast.tuoke.module.kaifa.dal.mysql.email.settingemail.SettingEmailMapper;
import cn.metast.tuoke.module.kaifa.utils.EmailUtils;
import cn.metast.tuoke.module.kaifa.utils.EmailHttpUtils;
import com.alibaba.fastjson.JSONObject;
import jakarta.mail.*;
import jakarta.mail.internet.MimeMessage;
import org.eclipse.angus.mail.imap.IMAPStore;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.metast.tuoke.module.kaifa.controller.admin.email.settingemail.vo.*;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;
import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.module.kaifa.enums.ErrorCodeConstants.*;

/**
 * 邮件配置 Service 实现类
 *
 * @author 精卫拓客
 */
@Service
@Validated
public class SettingEmailServiceImpl implements SettingEmailService {

    @Resource
    private SettingEmailMapper settingEmailMapper;
    @Resource
    private EmailUtils emailUtils;
    @Resource
    private EmailHttpUtils emailHttpUtils;
    @Resource
    private EmailSendService emailSendService;
    @Override
    public Long createSettingEmail(SettingEmailSaveReqVO createReqVO) {
        // 插入
        SettingEmailDO settingEmail = BeanUtils.toBean(createReqVO, SettingEmailDO.class);
        settingEmailMapper.insert(settingEmail);
        // 返回
        return settingEmail.getId();
    }

    @Override
    public void updateSettingEmail(SettingEmailSaveReqVO updateReqVO) {
        // 校验存在
        validateSettingEmailExists(updateReqVO.getId());
        // 更新
        SettingEmailDO updateObj = BeanUtils.toBean(updateReqVO, SettingEmailDO.class);
        settingEmailMapper.updateById(updateObj);
    }

    @Override
    public void deleteSettingEmail(Long id) {
        // 校验存在
        validateSettingEmailExists(id);
        // 删除
        settingEmailMapper.deleteById(id);
    }

    private void validateSettingEmailExists(Long id) {
        if (settingEmailMapper.selectById(id) == null) {
            throw exception(AUTOMATED_NOT_EXISTS);
        }
    }

    @Override
    public SettingEmailDO getSettingEmail(Long id) {
        return settingEmailMapper.selectById(id);
    }

    @Override
    public PageResult<SettingEmailDO> getSettingEmailPage(SettingEmailPageReqVO pageReqVO) {
        return settingEmailMapper.selectPage(pageReqVO);
    }
    @Override
    public JSONObject checkEmailStatus(SettingEmailRespVO reqVO){
        JSONObject result = new JSONObject();
        result.put("code",9);
        result.put("message","邮箱错误，请检查后重试");
        //1.验证收件测试
        Boolean receive = receiveCheck(reqVO);
        //2.验证发件测试
        Boolean send = sendCheck(reqVO);
        if(receive && send){
            result.put("code",0);
        }else if(receive && !send){
            result.put("message","邮箱错误，发件服务器失败，请检查后重试");
        }else if(!receive && send){
            result.put("message","邮箱错误，收件服务器失败，请检查后重试");
        }
        return result;
    }
    public Boolean receiveCheck(SettingEmailRespVO dto){
        String host = dto.getHost();
        Integer port = dto.getPort();
        String ssl = dto.getInSsl();
        String user = dto.getEmail();
        String password = dto.getPassword();

        String type = "imap";
        if(host.startsWith("pop")){
            type = "pop3";
        }
        Properties props =  emailUtils.getProperties(type,host,port,ssl,password);
        try {
            if(host.indexOf("edmtuoke.com") != -1) {
                Map<String, String> header = new HashMap<>();
                header.put("Content-Type", "application/json");
                JSONObject param = new JSONObject();
                param.put("weSettingDto", dto);
                JSONObject result = emailHttpUtils.doOkQuery("POST", "http://66.112.219.182:9077/mail/checkReceive", param.toJSONString(), header);
                if (ObjectUtil.isNotNull(result)) {
                    if (200 == result.getInteger("code")) {
                        return true;
                    }
                }
            } else{
                Session session = Session.getDefaultInstance(props);
                Store store = session.getStore("imap");
                Folder inbox = null;
                if("2".equals(dto.getEmailType())){
                    HashMap IAM = new HashMap();
                    //带上IMAP ID信息，由key和value组成，例如name，version，vendor，support-email等。
                    IAM.put("name","myname");
                    IAM.put("version","1.0.0");
                    IAM.put("support-email",dto.getEmail());
                    IMAPStore imapStore = (IMAPStore) store;
                    imapStore.connect(user, password);
                    imapStore.id(IAM);
                    inbox = imapStore.getFolder("INBOX");
                }else{
                    store.connect(user, password);
                    inbox = store.getFolder("INBOX");
                }
                inbox.open(Folder.READ_ONLY);
                inbox.close(false);
                store.close();
                return true;
            }
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("接收服务器连接失败：" + e.getMessage());
        }
        return false;
    }
    public Boolean sendCheck(SettingEmailRespVO dto){
        String sendEmail = dto.getEmail();
        String password = dto.getPassword();
        String outHost = dto.getOutHost();

        MimeMessage message = null;
        Session session = null;

        if(outHost.indexOf("edmtuoke.com") != -1){
            Map<String,String> header = new HashMap<>();
            header.put("Content-Type","application/json");
            JSONObject param = new JSONObject();
            param.put("weSettingDto",dto);
            JSONObject result = emailHttpUtils.doOkQuery("POST","http://66.112.219.182:9077/mail/checkMail", param.toJSONString(), header);
            if(ObjectUtil.isNotNull(result)){
                if(200 == result.getInteger("code")){
                    return true;
                }
            }
        }else{
            //微软、谷歌 需要挂代理
            if(sendEmail.indexOf("@hotmail.com") != -1 || sendEmail.indexOf("@outlook.com") != -1){
                session = emailSendService.setInitProxyData(dto);
            }else{
                session = emailSendService.setInitData(dto);
            }
            try {
                Transport transport = session.getTransport("smtp");
                transport.connect(sendEmail, password);
                transport.close();
                return true;
            } catch (MessagingException e) {
                System.out.println("发送服务器连接失败：" + e.getMessage());
            }
        }

        return false;
    }
}
