package devmanuals.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;

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
			String uname = request.getParameter("uname");
			String pass = request.getParameter("pass");
			
			if(uname.equals("admin") && pass.equalsIgnoreCase("admin")) {
				System.out.println("Client IP:: "+ getClientIpAddr(request));
				String ipAddress = getClientIpAddr(request);
				
				InputStream is = ServletRequestExample.class.getClassLoader().getResourceAsStream("AwsCredentials.properties");
				AWSCredentials credentials = new PropertiesCredentials(is);
				AmazonEC2Client amazonEC2Client = new AmazonEC2Client(credentials);
				IpPermission ipPermission = new IpPermission();
					    	
				ipPermission.withIpRanges(ipAddress + "/32")
				            .withIpProtocol("tcp")
				            .withFromPort(22)
				            .withToPort(22);
								
				AuthorizeSecurityGroupIngressRequest authorizeSecurityGroupIngressRequest = new AuthorizeSecurityGroupIngressRequest();			
					    	
					authorizeSecurityGroupIngressRequest.withGroupName("somnath-poc-sg").withIpPermissions(ipPermission);
					amazonEC2Client.authorizeSecurityGroupIngress(authorizeSecurityGroupIngressRequest);
					
					response.sendRedirect("welcome.jsp");
				
							
				
			} else {
				response.sendRedirect("index.jsp");
			}
		} catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public String getClientIpAddr(HttpServletRequest request) {  
		String ipAddress = null;  
		try {
			InetAddress thisIp = InetAddress.getLocalHost();
			ipAddress = thisIp.getHostAddress();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ipAddress;	
    }  
}