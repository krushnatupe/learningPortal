package login.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.jasper.tagplugins.jstl.core.Out;



/**
 * Servlet implementation class loginController
 */
@WebServlet("/loginController")
public class loginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public loginController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		HttpSession session = request.getSession();
		RequestDispatcher dispatcher = null;
		
		PrintWriter out = response.getWriter();
		
		if(email==null || email.equals("")) {
			request.setAttribute("status","Invalid_email");
			dispatcher = request.getRequestDispatcher("login.jsp");
			dispatcher.forward(request, response);
		}
		else if(password==null || password.equals("")) {
			request.setAttribute("status","Invalid_password");
			dispatcher = request.getRequestDispatcher("login.jsp");
			dispatcher.forward(request, response);
		}
		
		else {
			
			try {
				Class.forName("org.postgresql.Driver");
				
				con = DriverManager.getConnection("jdbc:postgresql://localhost/postgres","postgres","Pass@2803");
				if(con==null) {
					out.println("database is not connected");
				}
				else {
					pstmt = con.prepareStatement("SELECT * FROM registration WHERE email = ? AND password = ?");
					
					pstmt.setString(1, email);
					pstmt.setString(2, password);
					
					rs = pstmt.executeQuery();
					if(rs.next()) {
						session.setAttribute("username", rs.getString("name"));
						Integer userid = rs.getInt("id");
					    session.setAttribute("userid", userid);
						dispatcher = request.getRequestDispatcher("home.jsp");
					}
					else {
						request.setAttribute("status","failed");
						dispatcher = request.getRequestDispatcher("login.jsp");
					}
					dispatcher.forward(request, response);
				}
				
			}catch(Exception e) {
				out.print(e);
			}
		}
	}
}
