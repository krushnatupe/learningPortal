package registration.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import registration.implementation.registrationImplementation;
import registration.model.registration;

/**
 * Servlet implementation class registration
 */
@WebServlet("/registration")
public class registrationController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public registrationController() {
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
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String cpassword = request.getParameter("cpassword");
		RequestDispatcher dispatcher = null;
		HttpSession session = request.getSession();
		if(password.equals(cpassword))
		{
			registration reg = new registration(name, email, cpassword);
			List<registration> lst = new ArrayList<>();
			lst.add(reg);
			registrationImplementation impl = new registrationImplementation();
			PrintWriter pw = response.getWriter();
			int i = impl.insertData(lst);
			if(i>0)
			{
				request.setAttribute("status","success");
				dispatcher = request.getRequestDispatcher("login.jsp");
				dispatcher.forward(request, response);
				pw.write("Your data is inserted succefully");
			}
			else {
				request.setAttribute("status","failed");
				dispatcher = request.getRequestDispatcher("signup.jsp");
				dispatcher.forward(request, response);
			}
			
		}
		else {
			session.setAttribute("status","failed");
			response.sendRedirect("signup.jsp");
		}
	}

}
