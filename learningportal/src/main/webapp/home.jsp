<!DOCTYPE html>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.PreparedStatement"%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Learning Portal</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="d-flex flex-column min-vh-100">
	<%@ include file="navbar.jsp"%>

    <header class="py-5 bg-light">
        <div class="container">
            <h1>Welcome to Our Learning Portal</h1>
            <p>Explore our programming courses to boost your skills.</p>
        </div>
    </header>

    <div class="container mt-5">
        <h2 class="mb-4">Available Courses</h2>
        <div class="row">
        
        <%
    
        Connection con = null;
        ResultSet rs=null;
        try {
			 Class.forName("org.postgresql.Driver");  
			 con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres", "Pass@2803");
			 
			 PreparedStatement pst = con.prepareStatement("select * from courses");
		
		
			
			 rs = pst.executeQuery();
			
			 
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
        
        %>
        
       	<%
       		while(rs.next())
       		{
       			
       		
       	%>
       	<div class="col-md-6 mb-4">
                <div class="card h-60">
                    <img src=<%=rs.getString("image")%> class="card-img-top" height="300px" width="80px" alt=<%=rs.getString("title") %>>
                    <div class="card-body">
                        <h5 class="card-title"><%=rs.getString("title") %></h5>
                        <p class="card-text"><%=rs.getString("description") %></p>
                        <a href="./learningpage.jsp?id=1&courseid=<%=rs.getString("id") %>&coursename=<%=rs.getString("title") %>" class="btn btn-primary stretched-link">Explore Course</a>
                    </div>
                </div>
           </div>
       <%} %>
        </div>
    </div>

    <footer class="mt-auto bg-primary text-white">
        <div class="container text-center py-3">
            <p>© 2024 Learning Portal. All rights reserved.</p>
        </div>
    </footer>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>