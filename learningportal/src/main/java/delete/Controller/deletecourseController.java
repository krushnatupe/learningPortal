package delete.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class deletecourseController
 */
@WebServlet("/deletecourseController")
public class deletecourseController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public deletecourseController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		int id= Integer.parseInt(request.getParameter("title"));
		int i=0;

		HttpSession session = request.getSession();
		
		 Connection con = null;
		 try {
			 Class.forName("org.postgresql.Driver");  
			 con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres", "Pass@2803");
			 
			 PreparedStatement pst = con.prepareStatement("delete from courses where id=?");
			 pst.setInt(1, id);
			PrintWriter pw= response.getWriter();
			
			 i=pst.executeUpdate();
			
			 if(i>0)
			 {
				 session.setAttribute("deletecourse","success");
				 response.sendRedirect("admindeletecourse.jsp");
//				 RequestDispatcher view = request.getRequestDispatcher("admindeletecourse.jsp");
//				 view.forward(request,response);
			
			 }
			 else {
//				request.setAttribute("status", "failed");
//				 RequestDispatcher view = request.getRequestDispatcher("admindeletecourse.jsp");
//				 view.forward(request,response);
				 session.setAttribute("deletecourse","failed");
				 response.sendRedirect("admindeletecourse.jsp");
			 }
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
