<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    


<%
	int wcSeqno = Integer.parseInt(request.getParameter("wcSeqno"));

	String url_mysql = "jdbc:mysql://192.168.0.82:3306/WaGle?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
 	String pw_mysql = "qwer1234";
    String WhereDefault = "SELECT uImageName, wpReadPage, uSeqno, uLoginType, wpSeqno, uName FROM User AS u, WagleProgress AS wp, WagleUser AS wu WHERE u.uSeqno = wu.User_uSeqno AND wp.WagleUser_wuSeqno = wu.wuSeqno AND WagleCreate_wcSeqno =" + wcSeqno;
    int count = 0;
    
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        Statement stmt_mysql = conn_mysql.createStatement();

        ResultSet rs = stmt_mysql.executeQuery(WhereDefault); // 
%>
		{ 
  			"WagleProgress"  : [ 
<%
        while (rs.next()) {
            if (count == 0) {

            }else{
%>
            , 
<%
            }
%>            
			{
			"uImageName" : "<%=rs.getString(1) %>",
			"wpReadPage" : "<%=rs.getInt(2) %>",
			"uSeqno" : "<%=rs.getInt(3) %>",
			"uLoginType" : "<%=rs.getString(4) %>",
			"wpSeqno" : "<%=rs.getInt(5) %>",
			"uName" : "<%=rs.getString(6) %>"
			}
<%		
             count++;
        }
%>
		  ] 
		} 
<%		
        conn_mysql.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
	
%>