package com.hust.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class MailServerAuthenticator extends Authenticator {
	String userName = null;
	String password = null;

	public MailServerAuthenticator() {
	}
	
	public MailServerAuthenticator(String username, String password) {
		this.userName = username;
		this.password = password;
	}
	
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(userName, password);
	}
}
