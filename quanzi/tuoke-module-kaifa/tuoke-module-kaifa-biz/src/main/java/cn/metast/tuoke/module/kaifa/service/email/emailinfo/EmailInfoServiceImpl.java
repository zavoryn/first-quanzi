package cn.metast.tuoke.module.kaifa.service.email.emailinfo;

import cn.hutool.core.util.ObjectUtil;
import cn.metast.tuoke.module.kaifa.dal.dataobject.email.emailmx.*;
import cn.metast.tuoke.module.kaifa.dal.dataobject.email.settingemail.*;
import cn.metast.tuoke.module.kaifa.dal.mysql.email.settingemail.SettingEmailMapper;
import cn.metast.tuoke.module.kaifa.service.email.emailmx.EmailMxService;
import cn.metast.tuoke.module.system.dal.dataobject.user.AdminUserDO;
import cn.metast.tuoke.module.system.dal.mysql.user.AdminUserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import cn.metast.tuoke.module.kaifa.controller.admin.email.emailinfo.vo.*;
import cn.metast.tuoke.module.kaifa.dal.dataobject.email.emailinfo.*;
import cn.metast.tuoke.framework.common.pojo.PageResult;
import cn.metast.tuoke.framework.common.util.object.BeanUtils;
import cn.metast.tuoke.module.kaifa.dal.mysql.email.emailinfo.EmailInfoMapper;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import static cn.metast.tuoke.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.metast.tuoke.module.kaifa.enums.ErrorCodeConstants.*;
/**
 * 邮件内容 Service 实现类
 *
 * @author 精卫拓客
 */
@Service
@Validated
public class EmailInfoServiceImpl implements EmailInfoService {

    @Resource
    private EmailInfoMapper emailInfoMapper;
    @Resource
    private EmailMxService emailMxService;
    @Resource
    private AdminUserMapper userMapper;
    @Resource
    private SettingEmailMapper settingEmailMapper;
    @Override
    public Long createEmailInfo(EmailInfoSaveReqVO createReqVO) {
        // 插入
        EmailInfoDO emailInfo = BeanUtils.toBean(createReqVO, EmailInfoDO.class);
        emailInfoMapper.insert(emailInfo);
        // 返回
        return emailInfo.getId();
    }

    @Override
    public void updateEmailInfo(EmailInfoSaveReqVO updateReqVO) {
        // 校验存在
        validateEmailInfoExists(updateReqVO.getId());
        // 更新
        EmailInfoDO updateObj = BeanUtils.toBean(updateReqVO, EmailInfoDO.class);
        emailInfoMapper.updateById(updateObj);
    }

    @Override
    public void deleteEmailInfo(Long id) {
        // 校验存在
        validateEmailInfoExists(id);
        // 删除
        emailInfoMapper.deleteById(id);
    }

    private void validateEmailInfoExists(Long id) {
        if (emailInfoMapper.selectById(id) == null) {
            throw exception(AUTOMATED_NOT_EXISTS);
        }
    }

    @Override
    public EmailInfoDO getEmailInfo(Long id) {
        return emailInfoMapper.selectById(id);
    }

    @Override
    public PageResult<EmailInfoDO> getEmailInfoPage(EmailInfoPageReqVO pageReqVO) {
        return emailInfoMapper.selectPage(pageReqVO);
    }

    @Override
    public EmailMxDO chenkBindEmail(String email) {
        //返回邮箱类型 邮箱默认smtp/pops(不推荐，无法同步发件)
        EmailMxDO emailMxDO = null;
        //先验证是否是邮箱
        String regex = "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            try {
                //获取主域名  去判断属于那种类型
                String subDomain = email.substring(email.indexOf("@") + 1);
                String mainDomain = subDomain.substring(subDomain.lastIndexOf(".", subDomain.lastIndexOf(".")-1) + 1);
                emailMxDO = emailMxService.selectOneNew(mainDomain);
                if(ObjectUtil.isNull(emailMxDO)){
                    //获取域名mx值
                    String mx = domainMx(mainDomain);
                    //去除最后一个点
                    if(StringUtils.isNotEmpty(mx)){
                        emailMxDO = emailMxService.selectOneMX(mx);
                    }
                }
                if(ObjectUtil.isNotEmpty(emailMxDO)){
                    //查询是否已存在业务邮箱
                    QueryWrapper<SettingEmailDO> wrapper = new QueryWrapper<>();
                    wrapper.lambda().eq(StringUtils.isNotEmpty(email),SettingEmailDO::getEmail, email);
                    List<SettingEmailDO> weSettingEmailDtos = settingEmailMapper.selectList(wrapper);
                    if(CollectionUtils.isNotEmpty(weSettingEmailDtos)){
                        AdminUserDO adminUserDO = userMapper.selectById(weSettingEmailDtos.get(0).getUserId());
                        emailMxDO.setEmailType(adminUserDO.getEmail());
                    }
                    //查询是否已存在营销邮箱
                    //List<SettingSendEmailDO> sysSettingSendEmailPos = settingEmailMapper.selectList(new LambdaQueryWrapper<SettingSendEmailDO>().eq(SettingSendEmailDO::getEmail, email));
                    if(CollectionUtils.isNotEmpty(weSettingEmailDtos)){
                        emailMxDO.setSendEmailType("Y");
                    }
                }
            }catch (Exception e){
            }
        } else {
            //邮箱无效
        }
        return emailMxDO;
    }
    public String domainMx(String domain){
        String result = "";
        try {
            Hashtable<String,String> env = new Hashtable<>();
            env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
            DirContext ictx = new InitialDirContext(env);
            Attributes attrs = ictx.getAttributes(domain, new String[] {"MX"});
            Attribute attr = attrs.get("MX");

            if (ObjectUtil.isNotNull(attr)) {
                for (int i = 0; i < attr.size(); i++) {
                    String mxAttr = (String) attr.get(i);
                    String[] parts = mxAttr.split(" ");
                    if(parts.length == 2) {
                        result = parts[1];
                        if (result.endsWith(".")) {
                            result = result.substring(0, result.length() - 1);
                        }
                    }
                }
            }
        } catch (NamingException e) {
            return null;
        }
        return result;
    }
}
