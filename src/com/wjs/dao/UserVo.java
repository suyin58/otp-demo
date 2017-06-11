package com.wjs.dao;

public class UserVo {

	private String username;
	private String otpSk;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getOtpSk() {
		return otpSk;
	}
	public void setOtpSk(String otpSk) {
		this.otpSk = otpSk;
	}
	@Override
	public String toString() {
		return "UserVo [username=" + username + ", otpSk=" + otpSk + "]";
	}

	
}


