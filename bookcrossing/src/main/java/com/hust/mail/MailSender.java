package com.hust.mail;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public class MailSender {
	/**
	 * * 以文本格式发送邮件
	 * 
	 * @param mailInfo 待发送的邮件的信息
	 */
	public boolean sendTextMail(MailSenderInfoBean mailInfo) {
		// 判断是否需要身份认证
		MailServerAuthenticator authenticator = null;
		Properties pro = mailInfo.getProperties();
		if (mailInfo.isValidate()) {
			// 如果需要身份认证，则创建一个密码验证器
			authenticator = new MailServerAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
		}
		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
		try {
			// 根据session创建一个邮件消息
			Message mailMessage = new MimeMessage(sendMailSession);
			// 创建邮件发送者地址
			Address from = new InternetAddress(mailInfo.getFromAddress()); // 设置邮件消息的发送者
			mailMessage.setFrom(from);
			// 创建邮件的接收者地址，并设置到邮件消息中
			List<String> toAddress = null;
			if (mailInfo.getToAddress().size() > 1) {
				toAddress = mailInfo.getToAddress();
				Address[] address = new InternetAddress[toAddress.size()];
				for (int i = 0; i < toAddress.size(); i++) {
					address[i] = new InternetAddress(toAddress.get(i));
				}
				mailMessage.setRecipients(Message.RecipientType.TO, address);
			} else {
				toAddress = mailInfo.getToAddress();
				Address to = new InternetAddress(toAddress.get(0));
				// Message.RecipientType.TO属性表示接收者的类型为TO
				mailMessage.setRecipient(Message.RecipientType.TO, to);
			} // 设置邮件消息的主题 mailMessage.setSubject(mailInfo.getSubject());
			// 设置邮件消息发送的时间
			mailMessage.setSentDate(new Date());
			// 设置邮件消息的主要内容
			String mailContent = mailInfo.getContent();
			mailMessage.setText(mailContent);
			// 发送邮件
			Transport.send(mailMessage);
			return true;
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
		return false;
	}
		
	public void test() {     
		//这个类主要是设置邮件        
		MailSenderInfoBean mailInfo = new MailSenderInfoBean(); 
		mailInfo.setMailServerHost("smtp.163.com");      
		mailInfo.setMailServerPort("25");     
		mailInfo.setValidate(true);        
		mailInfo.setUserName("cxf19910321@163.com");    
		mailInfo.setPassword("？");//您的邮箱密码        mailInfo.setFromAddress("cxf19910321@163.com");  
		      
		List<String> address = new ArrayList<String>();      
		address.add("571310983@qq.com");     
		address.add("462676348@qq.com");  
		address.add("cxf19910321@163.com");       
		 
		mailInfo.setToAddress(address);     
		mailInfo.setSubject("邮件测试案例已经发到邮箱,请查收");        
		mailInfo.setContent("邮件测试案例已经发到邮箱,请查收,项目中用到了两个jar包,用的时候一并导入即可");     
		
		List<String> attachFileNames = new ArrayList<String>();       
		 //此处是你要得到的上传附件的文件路径        
		attachFileNames.add("pom.xml");   
		mailInfo.setAttachFileNames(attachFileNames);    
		//这个类主要来发送邮件       
		MailSender sms = new MailSender();   
		//采取文本格式发送        
		sms.sendTextMail(mailInfo); 
		}
}


