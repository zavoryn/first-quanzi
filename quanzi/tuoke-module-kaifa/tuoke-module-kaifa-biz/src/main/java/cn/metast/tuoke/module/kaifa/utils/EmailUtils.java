package cn.metast.tuoke.module.kaifa.utils;
import com.alibaba.druid.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Slf4j
@Service
public class EmailUtils{
    public static String toPlainText(final String html){
        Document doc = Jsoup.parse(html);
        doc.outputSettings(new Document.OutputSettings().prettyPrint(false)); // makes html() preserve linebreaks and spacing
        doc.select("br").append("\\n");
        doc.select("p").prepend("\\n\\n");

        Elements images = doc.select("img");
        for (Element image : images) {
            String altText = image.attr("alt");
            if (altText == null || altText.isEmpty()) {
                altText = "";/*[图片]*/
            }
            image.replaceWith(new Element(Tag.valueOf("span"), "").text(altText));
        }

        Elements links = doc.select("a");
        for (Element link : links) {
            String linkText = link.text();
            if (linkText == null || linkText.isEmpty()) {
                linkText = "";/*[链接]*/
            }
            link.replaceWith(new Element(Tag.valueOf("span"), "").text(linkText));
        }

        String str = doc.html().replaceAll("\\\\n", "\n");
        String ready = Jsoup.clean(str, "", org.jsoup.safety.Safelist.none(), new Document.OutputSettings().prettyPrint(false));
        // 获取纯文本的摘要
        return ready.isEmpty() ? "..." : ready.substring(0, Math.min(ready.length(), 100));
    }
    /**
     * 拉取邮件 javamail 初始化配置②
     * @param
     * @return
     */
    public Properties getProperties(String type, String host, Integer port, String ssl, String password){
        if(StringUtils.isEmpty(ssl)){
            ssl = "Y";//默认开启
        }
        //创建属性对象
        Properties props = System.getProperties();
        if(type.equals("imap")){
            props.put("mail.store.protocol", "imaps");
            props.put("mail.imap.host", host);
            props.put("mail.imap.port", port);
            props.put("mail.imap.connectiontimeout", "60000"); // 连接超时时间：10秒
            props.put("mail.imap.timeout", "60000"); // 读写超时时间：60秒
            props.put("mail.imap.protocol", "imap");
            if("Y".equals(ssl)){
                //SSL 连接
                props.put("mail.imap.ssl.enable", "true");
                props.put("mail.imap.ssl.trust", "*");
            }else{
                //非SSL 连接
                props.put("mail.imap.starttls.enable", "true");
                props.put("mail.imap.starttls.required", "true");
            }
            //验证是否为 应用码 ，应用码使用OAuth
            if((!StringUtils.equals("imap-mail.outlook.com",host) && !StringUtils.equals("outlook.office365.com",host)) || isPossibleAppPassword(password)){

                // 定义了要尝试的SASL机制的列表。这里的"XOAUTH2"是表示你正在使用OAuth 2.0进行身份验证。
                /**
                 * 从 JavaMail 1.5.5 开始，对 用于电子邮件的OAuth2 身份验证 的支持 是内置的，不再需要 SASL（尽管 SASL OAuth2 支持仍然有效）。
                 * 由于 OAuth2 使用“访问令牌”而不是密码，因此您需要将 JavaMail 配置为仅使用 XOAUTH2 机制。访问令牌作为密码传递，这显然不适用于任何其他身份验证机制。例如，要访问 Gmail：
                 */
//                props.put("mail.imap.auth.mechanisms", "XOAUTH2");
                /*
                 *   JavaMail 1.5.2 及更高版本 从 JavaMail 1.5.2 开始，支持 通过SASL XOAUTH2 机制进行OAuth2 身份验证。请将反馈发送至javamail_ww@oracle.com。
                 *   首次使用 SASL 支持时，SASL XOAUTH2 提供程序将 添加到 Java 安全配置中 。应用程序必须具有权限SecurityPermission("insertProvider.JavaMail-OAuth2")。
                 *   由于 OAuth2 使用“访问令牌”而不是密码，因此您需要将 JavaMail 配置为仅使用 SASL XOAUTH2 机制。访问令牌作为密码传递，这显然不适用于任何其他身份验证机制。例如，要访问 Gmail：
                 * */
//                props.put("mail.imap.sasl.enable", "true");
                props.put("mail.imap.sasl.mechanisms", "XOAUTH2");
                // 如果设为"false"，JavaMail API将尝试使用LOGIN认证机制。
                props.put("mail.imap.auth.login.disable", "true");
                // 如果设为"false"，JavaMail API将尝试使用PLAIN认证机制。
                props.put("mail.imap.auth.plain.disable", "true");
            }
        }else if(type.equals("pop3")){
            props.put("mail.pop3.host", host);
            props.put("mail.pop3.port", port);
            props.put("mail.pop3.protocol", "pop3");
            props.put("mail.pop3.ssl.enable", "true");
            props.put("mail.pop3.ssl.trust", "*");
            props.put("mail.pop3.sasl.mechanisms", "XOAUTH2");
            props.put("mail.pop3.auth.login.disable", "true");
            props.put("mail.pop3.auth.plain.disable", "true");
        }
        return props;
    }
    public boolean isPossibleAppPassword(String input) {
        if (input == null || input.length() != 16) {
            return false;
        }
        for (char c : input.toCharArray()) {
            if (!Character.isLowerCase(c) && !Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}
