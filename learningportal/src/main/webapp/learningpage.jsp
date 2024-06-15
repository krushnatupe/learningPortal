<!doctype html>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Connection"%>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Bootstrap demo</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <style>
      .content {
    margin-top: 50px; /* Adjust this value based on your navbar height */
    padding: 20px;
  }
    </style>
    
    <script>
      var isSpeaking = false;
      var msg = new SpeechSynthesisUtterance();
      var textToSpeak = '';

      // Initialize the speech synthesis utterance
      window.onload = function() {
        textToSpeak = document.querySelector('.content').innerText;
        msg.text = textToSpeak;
        msg.onend = function(event) {
          console.log('Finished in ' + event.elapsedTime + ' seconds.');
          isSpeaking = false;
          updateButton();
        };
      }

      function toggleSpeech() {
        if (!isSpeaking) {
          window.speechSynthesis.speak(msg);
        } else {
          window.speechSynthesis.cancel();
        }
        isSpeaking = !isSpeaking;
        updateButton();
      }

      function updateButton() {
        var button = document.getElementById('speakButton');
        if (isSpeaking) {
          button.innerText = 'Pause';
        } else {
          button.innerText = 'Read Content';
        }
      }
      
    </script>
  </head>
  <body>
    <div>

    
    <nav class="navbar navbar-dark bg-dark fixed-top">
      <div class="container-fluid">
        <a class="navbar-brand" href="home.jsp"><%=request.getParameter("coursename") %></a>
        <div class="ms-auto p-1">
                <button id="speakButton" class="btn btn-danger btn-rounded" data-mdb-ripple-init onclick="toggleSpeech()">Read Content</button>
        </div>
        <button class="navbar-toggler" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasDarkNavbar" aria-controls="offcanvasDarkNavbar" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="offcanvas offcanvas-end text-bg-dark" tabindex="-1" id="offcanvasDarkNavbar" aria-labelledby="offcanvasDarkNavbarLabel">
          <div class="offcanvas-header">
            <h5 class="offcanvas-title" id="offcanvasDarkNavbarLabel"><%=request.getParameter("coursename") %></h5>
            <button type="button" class="btn-close btn-close-white" data-bs-dismiss="offcanvas" aria-label="Close"></button>
          </div>
          <div class="offcanvas-body">
            <ul class="navbar-nav justify-content-end flex-grow-1 pe-3">
            
            <%
    		int courseid = Integer.parseInt(request.getParameter("courseid"));
        	Connection con = null;
        	ResultSet rs=null;
      
        	String courseName=null;
        try {
			 Class.forName("org.postgresql.Driver");  
			 con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres", "Pass@2803");
			 
			 PreparedStatement pst = con.prepareStatement("select * from learningdata where courseid=?");
			
			pst.setInt(1,courseid);
			
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
               <li class="nav-item">
                <a class="nav-link active" aria-current="page" href="./learningpage.jsp?id=<%=rs.getInt("id") %>&courseid=<%=courseid %>&coursename=<%=request.getParameter("coursename") %>"><%=rs.getString("title") %></a>
              </li>
           <%} %> 
           <li class="nav-item">
                <a class="nav-link active" aria-current="page" href="QuizServlet?course=<%=request.getParameter("coursename") %>&courseid=<%=courseid %>" >Quiz</a>
            </li>   
          </div>
        </div>
      </div>
    </nav>
    
    
   		<% 
   		
   	
    	ResultSet result=null;
    try {
    		
		 int id = Integer.parseInt(request.getParameter("id"));
		 PreparedStatement pstt = con.prepareStatement("select * from learningdata where id=?");
		 pstt.setInt(1, id);
		
		 result = pstt.executeQuery();
		
		 
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	 
    	%>
    	
    </script>
  </div>
    <div class="content">
    <% while(result.next()){ %>
    
      <p>
        <h1><%= result.getString("title") %></h1>
       	<%= result.getString("description") %>
      </p>
       	<%} %>
    </div>
   
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
  </body>
</html>