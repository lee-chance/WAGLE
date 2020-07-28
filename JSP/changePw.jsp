<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	request.setCharacterEncoding("utf-8");

	int uSeqno = Integer.parseInt(request.getParameter("uSeqno"));
	String uId = request.getParameter("uId");
	String uPassword = request.getParameter("uPassword");
	String uEmail = request.getParameter("uEmail");
	String uName = request.getParameter("uName");
	String uImageName = request.getParameter("uImageName");
	String uBirthDate = request.getParameter("uBirthDate");

	String url_mysql = "jdbc:mysql://192.168.0.82/WaGle?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
 	String pw_mysql = "qwer1234";
	
	PreparedStatement ps = null;

    Class.forName("com.mysql.cj.jdbc.Driver");
    Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
    Statement stmt_mysql = conn_mysql.createStatement();

   	try {
    		

		String A = "UPDATE User SET uPassword=? WHERE uSeqno=?";

		ps = conn_mysql.prepareStatement(A);
		ps.setString(1, uPassword);
		ps.setInt(2, uSeqno);

		ps.executeUpdate();

		A = "INSERT INTO UserBackup (uSeqno, uId, uPassword, uEmail, uLoginType, uName, uImageName, uBirthDate, uDate) VALUES (?, ?, ?, ?, 'wagle', ?, ?, ?, now())";

		ps = conn_mysql.prepareStatement(A);
	
		ps.setInt(1, uSeqno);
		ps.setString(2, uId);
		ps.setString(3, uPassword);
		ps.setString(4, uEmail);
		ps.setString(5, uName);
		ps.setString(6, uImageName);
		ps.setString(7, uBirthDate);

		ps.executeUpdate();
		 
	   	conn_mysql.close();
	} catch (Exception e){
    	e.printStackTrace();
    	conn_mysql.close();
	}

%>