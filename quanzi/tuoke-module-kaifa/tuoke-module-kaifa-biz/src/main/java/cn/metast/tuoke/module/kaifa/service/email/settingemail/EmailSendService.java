package cn.metast.tuoke.module.kaifa.service.email.settingemail;
import cn.metast.tuoke.module.kaifa.controller.admin.email.settingemail.vo.SettingEmailRespVO;
import jakarta.mail.Session;

public interface EmailSendService {

	//public JSONObject sendBusinessMail(SettingEmailRespVO dto, String[] filePaths, SettingEmailRespVO weSettingDto, SettingSendEmailDto sysSettingDto, String emailType);
	public int sendAttachmentsMailSop(SettingEmailRespVO weSettingEmailDto, String to, String subject, String content, String filePath);
	public Session setInitData(SettingEmailRespVO settingDto);
	public Session setInitProxyData(SettingEmailRespVO settingDto);
}
