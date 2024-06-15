<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.learningportal.dao.TestResultDAO, com.learningportal.model.TestResult"%>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>My Performance</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<%@ include file="navbar.jsp"%>
    <div class="container mt-4">
        <h1 class="text-center mb-4">My Performance</h1>
        <table class="table table-striped">
            <thead>
                <tr>
                <th>Course</th>
                    <th>Test Date</th>
                    
                    <th>Score</th>
                    <th>Time Taken (seconds)</th>
                    <th>Feedback From AI</th>
                </tr>
            </thead>
            <tbody>
              <%
                    Connection con = null;
                    ResultSet rs=null;
                    int userid = (Integer) session.getAttribute("userid");
                    try {
            			 Class.forName("org.postgresql.Driver");  
            			 con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres", "Pass@2803");
            			 
            			 PreparedStatement pst = con.prepareStatement("select * from user_test_results where user_id = ?");
            		
            			pst.setInt(1,userid);
            			
            			 rs = pst.executeQuery();
            			
            			 
            		} catch (ClassNotFoundException | SQLException e) {
            			// TODO Auto-generated catch block
            			e.printStackTrace();
            		}	 
                    %>
                <% 
                // Simulating a user ID for demonstration; in a real application, fetch this from session or user context
                
               while(rs.next()){
                %>
                <tr>
                    
                    <%
                    ResultSet rs2 = null;
                    int courseid = Integer.parseInt(rs.getString("course_id"));
                    try {
           			 
           			 PreparedStatement pst2 = con.prepareStatement("select title from courses where id = ?");
           		
           			pst2.setInt(1,courseid);
           			
           			 rs2 = pst2.executeQuery();
           			
           			 
           		} catch (SQLException e) {
           			// TODO Auto-generated catch block
           			e.printStackTrace();
           		}	
                    %>
                    <%while(rs2.next()){ %>
                    <td><%=rs2.getString("title") %>
                    <%} %>
                    </td>
                    <td><%= rs.getString("test_date") %></td>
                    <td><%= rs.getInt("score") %></td>
                    <td><%= rs.getInt("time_taken") %></td>
                    <td><%= rs.getString("feedback") %></td>
                </tr>
                <% } %>
            </tbody>
        </table>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js"></script>
</body>
</html>
