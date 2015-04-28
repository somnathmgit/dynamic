package devmanuals.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletRequestExample extends HttpServlet {

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {			
			String uname = request.getParameter("uname");
			String pass = request.getParameter("pass");
			
			if(uname.equals("admin") && pass.equalsIgnoreCase("admin")) {
				response.sendRedirect("welcome.jsp");
			} else {
				response.sendRedirect("index.jsp");
			}
		} catch(Exception ex){
			ex.printStackTrace();
		}
	}
}