package cn.metast.tuoke.module.kaifa.service.email.settingemail;
import cn.hutool.core.util.ObjectUtil;
import cn.metast.tuoke.framework.common.exception.ServiceException;
import cn.metast.tuoke.module.kaifa.controller.admin.email.settingemail.vo.SettingEmailRespVO;
import com.alibaba.fastjson.JSONObject;
import io.micrometer.common.util.StringUtils;
import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.net.URL;
import java.util.Properties;

import static cn.metast.tuoke.module.kaifa.enums.ErrorCodeConstants.AUTOMATED_NOT_EXISTS_MAIL;

@Service
public class EmailSendServiceImpl implements EmailSendService {
	private static Logger log = LoggerFactory.getLogger(EmailSendServiceImpl.class);
	private JavaMailSenderImpl mailSender;
	/**
	 * 初始化邮件发送数据
	 */
	@Override
	public Session setInitData(SettingEmailRespVO settingDto) {

		// 创建邮件发送服务器
		mailSender = new JavaMailSenderImpl();
		mailSender.setHost(settingDto.getOutHost());
		mailSender.setUsername(settingDto.getEmail());
		mailSender.setPassword(settingDto.getPassword());
		mailSender.setDefaultEncoding("utf-8");
		// 加认证机制
		Properties javaMailProperties = new Properties();
		// 设置使用SMTP协议
		javaMailProperties.put("mail.smtp.timeout", 30000); // 10秒
		javaMailProperties.put("mail.smtp.connectiontimeout", 30000); // 10秒
		javaMailProperties.setProperty("mail.transport.protocol", "smtp");
		javaMailProperties.setProperty("mail.smtp.host", settingDto.getOutHost());
		javaMailProperties.setProperty("mail.smtp.port", String.valueOf(settingDto.getOutPort()));
		javaMailProperties.setProperty("mail.smtp.socketFactort.port", String.valueOf(settingDto.getOutPort()));
		javaMailProperties.setProperty("mail.smtp.auth", "true");
		if("Y".equals(settingDto.getOutSsl())){
			javaMailProperties.setProperty("mail.smtp.ssl.enable", "true");
//			javaMailProperties.setProperty("mail.smtp.starttls.enable", "false");
//			if(settingDto.getEmail().indexOf("@edmtuoke.com") != -1 || settingDto.getEmail().indexOf("@emartshow.com") != -1 || settingDto.getEmail().indexOf("@hi1024.com") != -1){
//				javaMailProperties.put("mail.smtp.ssl.socketFactory", createTrustAllSocketFactory());
//			}
		}else{
			javaMailProperties.setProperty("mail.smtp.ssl.enable", "false");
			javaMailProperties.setProperty("mail.smtp.starttls.enable", "true");
//			javaMailProperties.put("mail.smtp.ssl.socketFactory", createTrustAllSocketFactory());
		}
//		try {
//			MailSSLSocketFactory sf = new MailSSLSocketFactory();
//			sf.setTrustAllHosts(true);
//			javaMailProperties.put("mail.smtp.ssl.trust", "*");
//			javaMailProperties.put("mail.smtp.ssl.socketFactory", sf);
//		}catch (Exception e){
//			e.printStackTrace();
//		}
		javaMailProperties.put("mail.mime.charset", "UTF-8");
		javaMailProperties.put("mail.smtp.ssl.protocols", "TLSv1.2");
		javaMailProperties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		Session session = Session.getInstance(javaMailProperties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				// 登录用户名和密码（注意：某些邮箱需要使用SMTP的授权码）
				return new PasswordAuthentication(settingDto.getEmail(), settingDto.getPassword());
			}
		});
		mailSender.setJavaMailProperties(javaMailProperties);
		log.debug("初始化邮件发送信息完成");
		return session;
	}

	@Override
	public Session setInitProxyData(SettingEmailRespVO settingDto) {
		// 创建邮件发送服务器
		mailSender = new JavaMailSenderImpl();
		mailSender.setHost(settingDto.getOutHost());
		mailSender.setUsername(settingDto.getEmail());
		mailSender.setPassword(settingDto.getPassword());
		mailSender.setDefaultEncoding("utf-8");

		String ip = "";
		Integer port = null;
		String proxyUsername = "";
		String proxyPassword = "";
		String proxyInfo = "";//DictUtil.getConfigCacheToStr("sys.google.proxy");缓存待出来，到时按新项目缓存
		if(StringUtils.isNotEmpty(proxyInfo)){
			try {
				JSONObject proxyJson = JSONObject.parseObject(proxyInfo);
				ip = proxyJson.getString("ip");
				port = proxyJson.getInteger("port");
				proxyUsername = proxyJson.getString("username");
				proxyPassword = proxyJson.getString("password");
			}catch (Exception e){
				ip = "";
				port = null;
			}
		}
		if(StringUtils.isEmpty(ip) || ObjectUtil.isNull(port)){
			throw new ServiceException(AUTOMATED_NOT_EXISTS_MAIL);
		}

		//设置系统属性:代理服务器
//		System.setProperty("proxySet", "true");
//		System.setProperty("http.proxyHost", ip);
//		System.setProperty("http.proxyPort", String.valueOf(port));

		// 用于连接邮件服务器的参数配置（发送邮件时才需要用到）
		Properties props = new Properties();
		// 设置使用SMTP协议
		props.setProperty("mail.transport.protocol", "smtp");
		// 设置SMTP服务器地址
		props.setProperty("mail.smtp.host", settingDto.getOutHost());
		// 设置SMTP服务器端口
		props.setProperty("mail.smtp.port", String.valueOf(settingDto.getOutPort()));
		// 设置需要验证
		props.setProperty("mail.smtp.auth", "true");
//		props.put("mail.smtp.auth.mechanisms", "XOAUTH2"); // 或者尝试 "PLAIN", "NTLM", "DIGEST-MD5" 等
//		// 设置SSL
//		props.setProperty("mail.smtp.ssl.enable", "true");  // Enable SSL
		// 设置使用TLS安全协议
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.starttls.required", "true");
		props.put("mail.mime.charset", "UTF-8");

		props.put("mail.smtp.proxy.host", ip);
		props.put("mail.smtp.proxy.port", String.valueOf(port));

		// 创建Session实例对象
		String finalProxyUsername = proxyUsername;
		String finalProxyPassword = proxyPassword;
//		Session session = Session.getInstance(props, new Authenticator() {
//			protected PasswordAuthentication getPasswordAuthentication() {
//				// 登录用户名和密码（注意：某些邮箱需要使用SMTP的授权码）
//				return new PasswordAuthentication(settingDto.getEmail(), settingDto.getPassword());
//			}
//		});
//		session.setDebug(true);
		// 创建Session实例对象
		Session session = Session.getInstance(props);
		mailSender.setJavaMailProperties(props);
		return session;
	}

	// 创建一个自定义的信任所有证书的SSL套接字工厂
	private SSLSocketFactory createTrustAllSocketFactory() {
		try {
			TrustManager[] trustAllCerts = new TrustManager[]{
					new X509TrustManager() {
						@Override
						public java.security.cert.X509Certificate[] getAcceptedIssuers() {
							return null;
						}
						@Override
						public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
						}
						@Override
						public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
						}
					}
			};

			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			return sc.getSocketFactory();
		}catch (Exception e){

		}
		return (SSLSocketFactory) SSLSocketFactory.getDefault();
	}

    @Override
    public int sendAttachmentsMailSop(SettingEmailRespVO weSettingEmailDto, String to, String subject, String content, String filePath) {
		try {
			String email = weSettingEmailDto.getEmail();

			setInitData(weSettingEmailDto);
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
				helper.setFrom(email);
				helper.setTo(to);
				helper.setSubject(subject);
				helper.setText(content, true);
				if (StringUtils.isNotEmpty(filePath)) {
					FileSystemResource file = new FileSystemResource(new File(filePath));
					String fileName = file.getFilename();
					helper.addAttachment(fileName, file);
				}
				mailSender.send(message);
			return 1;
		}catch (Exception e){
			log.info("邮件发送失败！");
			return 0;
		}
    }

    public String getFilePath(String url){
		//存放到本地
		File inputFile =getFile(url);
		String absolutePath = inputFile.getAbsolutePath();
		return absolutePath;
	}

	/**
	 * 创建一个临时文件
	 * @param url 远端文件Url
	 * @return File
	 */
	private File getFile(String url) {
		// 对本地文件命名
		String fileName = "";

		if(url.indexOf("?Expires=") != -1){
			String url2 = url.split("Expires=")[0].replace("?","");
			fileName = url2.substring(url2.lastIndexOf("."), url2.length());
		}else{
			fileName = url.substring(url.lastIndexOf("."), url.length());
		}

		// 创建本地文件指针以及URL指针
		File file = null;
		URL urlFile;

		try {
			// 创建一个临时路径
			file = File.createTempFile("file", fileName);
			log.info("tempFile:->{}", file.getAbsolutePath());

			// 下载文件
			urlFile = new URL(url);

			// 使用try-with-resources方法，自动关闭资源
			try (InputStream inStream = urlFile.openStream();
				 OutputStream os = new FileOutputStream(file, true)) {
				int bytesRead = 0;
				byte[] buffer = new byte[8192];

				// 读取远程文件并写入本地文件
				while ((bytesRead = inStream.read(buffer, 0, 8192)) != -1) {
					os.write(buffer, 0, bytesRead);
				}
			} catch (Exception e) {
				log.error("创建临时文件出错：-》{}", e.getMessage());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}
}
