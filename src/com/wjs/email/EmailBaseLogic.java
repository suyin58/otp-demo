package com.wjs.email;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.sun.mail.smtp.SMTPSSLTransport;

/**
 * 
 *
 * 
 * @author weicd@wjs.com email
 * @date 2016年11月3日 上午8:54:51
 * 
 *
 */
public class EmailBaseLogic {
	private static final Logger LOGGER = Logger.getLogger(EmailBaseLogic.class);
	/**
	 * 邮箱发送服务器
	 */
	private String host = "smtp.qq.com";
	/**
	 * 端口
	 */
	private int port = 465;
	/**
	 * 邮箱账号
	 */
	private String user = "310082253@qq.com";
	/**
	 * 邮箱密码
	 */
	private String pass = "nthzhhophhiecaji";
	/**
	 * 目标邮箱
	 */
	private String to;
	/**
	 * 是否ssl
	 */
	private Boolean auth = false;
	/**
	 * 是否调试
	 */
	private Boolean debug = false;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public Boolean getAuth() {
		return auth;
	}

	public void setAuth(Boolean auth) {
		this.auth = auth;
	}

	public Boolean getDebug() {
		return debug;
	}

	public void setDebug(Boolean debug) {
		this.debug = debug;
	}

	/**
	 * 构造函数
	 * 
	 * @param host
	 * @param port
	 * @param user
	 * @param pass
	 * @param to
	 * @param auth
	 */
	public EmailBaseLogic(String host, int port, String user, String pass, String to, Boolean auth, Boolean debug) {
		super();
		this.host = host;
		this.port = port;
		this.user = user;
		this.pass = pass;
		this.to = to;
		this.auth = auth;
		this.debug = debug;
	}

	/**
	 * 因为已经定义过自定义构造函数，删除掉这玩意儿自动注入会失败
	 */
	public EmailBaseLogic() {

	}

	/**
	 * 发送邮件
	 * 
	 * @param title
	 *            主题
	 * @param content
	 *            内容
	 * @param isSSL
	 *            是否使用SSL
	 * @return 发信是否成功
	 */
	public boolean send(String title, String content) {
		boolean result = true;
		try {
			Properties pro = new Properties();
			pro.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			pro.setProperty("mail.smtp.port", String.valueOf(port));
			pro.setProperty("mail.smtp.socketFactory.port", String.valueOf(port));

			Session session = Session.getDefaultInstance(pro);
			session.setDebug(debug); // 是否在控制台打出语句

			MimeMessage message = new MimeMessage(session); // 定义从哪个邮箱到哪个邮箱的地址和内容
			message.setFrom(new InternetAddress(user));
			message.addRecipients(Message.RecipientType.TO, to);
			message.setSubject(title);
			message.setText(content);
			message.saveChanges();

			Transport tran = null;
			if (auth) {
				tran = new SMTPSSLTransport(session, null); // 使用SSL
			} else {
				tran = session.getTransport("smtp"); // 通过SMTP效验用户，密码等进行连接
			}
			tran.connect(host, port, user, pass);
			tran.sendMessage(message, message.getAllRecipients());
			tran.close();
		} catch (Exception e) {
			LOGGER.error("邮件发送失败", e);
			result = false;
		}
		return result;
	}

	public boolean sendWithPic(String title, String content, String imagePath) {
		boolean result = true;
		try {
			Properties pro = new Properties();
			pro.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			pro.setProperty("mail.smtp.port", String.valueOf(port));
			pro.setProperty("mail.smtp.socketFactory.port", String.valueOf(port));

			Session session = Session.getDefaultInstance(pro);
			session.setDebug(debug); // 是否在控制台打出语句

			MimeMessage message = new MimeMessage(session); // 定义从哪个邮箱到哪个邮箱的地址和内容
			message.setFrom(new InternetAddress(user));
			message.addRecipients(Message.RecipientType.TO, to);
			message.setSubject(title);

			Multipart multipart = new MimeMultipart("related");

			BodyPart htmlPart = new MimeBodyPart();
			StringBuffer context = new StringBuffer("");
			String mailTemplateHeader = "<div style='margin:0 auto; width:900px; box-shadow:0 0 0 rgba(0, 0, 0, 0.2);font:14px/1.2 Helvetica,Microsoft Yahei,Hiragino Sans GB,WenQuanYi Micro Hei,sans-serif;'><div style='padding: 20px 40px;color:#333333;min-height:300px;line-height:30px;background-color:#ffffff;'><p></p><p style=\\'text-indent:2em;\\'>";
			String mailTemplateFooter = "</p><p style='text-align:right;padding:0 22px 0 0;margin:20px 0 0 0;'>网金社</p></div><div style='background:#efefef;color:#999999;text-align:center;padding: 10px 0;'><p>此为系统邮件，请勿回复</p><p>本页面内容最终解释权归浙江互联网金融资产交易中心股份有限公司拥有</p><p>网址:<a href='https://www.wjs.com/' style='color:#01abff' target='_blank'>www.wjs.com</a>&nbsp;&nbsp;&nbsp;客服电话:400-998-7676</p></div></div>";
			context.append(mailTemplateHeader).append(content).append(mailTemplateFooter);
			htmlPart.setContent(context.toString(), "text/html;charset=utf-8");
			multipart.addBodyPart(htmlPart);

			BodyPart imgPart = new MimeBodyPart();
			DataSource fds = new FileDataSource(imagePath);

			imgPart.setDataHandler(new DataHandler(fds));
			imgPart.setHeader("Content-ID", "<image>");

			multipart.addBodyPart(imgPart);
			message.setContent(multipart);

			Transport tran = null;
			if (auth) {
				tran = new SMTPSSLTransport(session, null); // 使用SSL
			} else {
				tran = session.getTransport("smtp"); // 通过SMTP效验用户，密码等进行连接
			}
			tran.connect(host, port, user, pass);
			tran.sendMessage(message, message.getAllRecipients());
			tran.close();
		} catch (Exception e) {
			LOGGER.error("邮件发送失败", e);
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	public void sendEmail(String to, String title, String content) {

		System.out.println(String.format("发件人:%s, 收件人:%s", user, to));
		new EmailBaseLogic(host, port, user, pass, to, false, false).send(title, content);
	}

	public void sendWithPic(String to, String title, String content, String imagePath) {
		System.out.println(String.format("发件人:%s, 收件人:%s", user, to));
		new EmailBaseLogic(host, port, user, pass, to, false, false).sendWithPic(title, content, imagePath);
	}

	/**
	 * 
	 * 
	 * @return
	 * @author weicd@wjs.com email
	 * @date 2017年3月1日 下午4:56:47
	 */
	public static String getLocalIP() {
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		byte[] ipAddr = addr.getAddress();
		String ipAddrStr = "";
		for (int i = 0; i < ipAddr.length; i++) {
			if (i > 0) {
				ipAddrStr += ".";
			}
			ipAddrStr += ipAddr[i] & 0xFF;
		}
		return ipAddrStr;
	}

}
