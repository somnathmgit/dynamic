package devmanuals.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserPrincipal extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		 String s = "Hello";
	  		
        // get remote user using getUserPrincipal()
        java.security.Principal principal = request.getUserPrincipal();
        String remoteUserName = "";
        if( principal != null )
         	remoteUserName = principal.getName();
        
        // get remote user using getRemoteUser()        
        String remoteUser = request.getRemoteUser();

        // check if remote user is granted Mgr role
        boolean isMgr = request.isUserInRole("Mgr");

        // display Hello username for managers and bob. 
        if ( isMgr || remoteUserName.equals("bob") )
            s = "Hello " + remoteUserName;

		 String message =  "<html> \n" +"<head><title>Hello Servlet</title></head>\n" +	"<body> /n" +"<h1> "  +s+ "</h1>/n "; 
		byte[] bytes = message.getBytes();
		
		// displays "Hello" for ordinary users 
        // and displays "Hello username" for managers and "bob".
        response.getOutputStream().write(bytes);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}