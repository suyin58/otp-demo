package com.wjs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDao {
	
	public static void main(String[] args) {
		UserDao dao = new UserDao();
		UserVo vo = new UserVo();
		String name = "namtest";
		vo.setUsername(name);
		vo.setOtpSk("otptest");
//		dao.add(vo);
		vo = dao.getUserByName(name);
		System.out.println(vo);
	}
	public void add(UserVo user){
		
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = MyConn.getConn();
			stmt = conn.createStatement();
			stmt.execute("insert into  t_user(username, otp_sk) values('"+user.getUsername()+"', '"+user.getOtpSk()+"')");
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(null != conn){
				try {
					conn.close();
				} catch (SQLException e) {
					// ignore
				}
			}

			// Close other
		}
	}
	
	public UserVo getUserByName(String name){
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = MyConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select username,otp_sk from t_user where username = '"+name+"'");
			if(rs.next()){
				UserVo vo = new UserVo();
				vo.setUsername(rs.getString("username"));
				vo.setOtpSk(rs.getString("otp_sk"));
				return vo;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(null != conn){
				try {
					conn.close();
				} catch (SQLException e) {
					// ignore
				}
			}

			// Close other
		}
		return null;
	}
}
