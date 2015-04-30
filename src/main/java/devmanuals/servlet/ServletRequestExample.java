package devmanuals.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.AuthorizeSecurityGroupIngressRequest;
import com.amazonaws.services.ec2.model.IpPermission;

public class ServletRequestExample extends HttpServlet {

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {		
			PrintWriter out = response.getWriter();
			String uname = request.getParameter("uname");
			String pass = request.getParameter("pass");
			
			if(uname.equals("admin") && pass.equalsIgnoreCase("admin")) {
				response.sendRedirect("welcome.jsp");
				
				InputStream is = ServletRequestExample.class.getClassLoader().getResourceAsStream("AwsCredentials.properties");
				System.out.println("Inputstream: "+is);
				AWSCredentials credentials = new PropertiesCredentials(is);
				AmazonEC2Client amazonEC2Client = new AmazonEC2Client(credentials);
				out.println("Access Key: "+credentials);
				out.println("Access Key: "+credentials.getAWSAccessKeyId());
				IpPermission ipPermission = new IpPermission();
					    	
				ipPermission.withIpRanges("111.111.111.111/32", "150.150.150.150/32")
				            .withIpProtocol("tcp")
				            .withFromPort(22)
				            .withToPort(22);
				
				AuthorizeSecurityGroupIngressRequest authorizeSecurityGroupIngressRequest = new AuthorizeSecurityGroupIngressRequest();			
					    	
					authorizeSecurityGroupIngressRequest.withGroupName("somnath-poc-sg").withIpPermissions(ipPermission);
					amazonEC2Client.authorizeSecurityGroupIngress(authorizeSecurityGroupIngressRequest);
					
			} else {
				response.sendRedirect("index.jsp");
			}
		} catch(Exception ex){
			ex.printStackTrace();
		}
	}
}