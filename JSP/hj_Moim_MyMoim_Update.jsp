<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>  
      
<%
	request.setCharacterEncoding("utf-8");
	String maGrade = request.getParameter("maGrade");
	String muSeqno = request.getParameter("muSeqno");
	
	System.out.println(maGrade);
	System.out.println(muSeqno);
	
//------
	String url_mysql = "jdbc:mysql://192.168.0.82:3306/WaGle?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=FALSE";
	String id_mysql = "root";
	String pw_mysql = "qwer1234";

	PreparedStatement ps = null;
	try{
	    Class.forName("com.mysql.jdbc.Driver");
	    Connection conn_mysql = DriverManager.getConnection(url_mysql,id_mysql,pw_mysql);
	    Statement stmt_mysql = conn_mysql.createStatement();
	    
		String query = "update MoimAdminister set maGrade = ? where MoimUser_muSeqno = ? ;";
		ps = conn_mysql.prepareStatement(query);
		
		ps.setString(1, maGrade);
		ps.setInt(2, Integer.parseInt(muSeqno));
		
		ps.executeUpdate();	// 실행.	    
	    
	    conn_mysql.close();
	} 	
	catch (Exception e){
	    e.printStackTrace();
	}
%>