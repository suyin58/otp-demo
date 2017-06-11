数据库表结构：
CREATE TABLE `t_user` (
  `username` varchar(30) COLLATE utf8_bin NOT NULL,
  `otp_sk` varchar(64) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


创建用户：
	运行RegistTest的main方法，注意修改f_temp[临时二维码图片存放目录]，email[接收二维码图片邮箱地址]
	
工程部署在tomcat下，登录地址：
	http://127.0.0.1:8580/login.jsp

