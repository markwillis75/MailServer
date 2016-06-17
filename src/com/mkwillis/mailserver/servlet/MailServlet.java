package com.mkwillis.mailserver.servlet;

import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import com.mkwillis.mailserver.beans.*;
import com.mkwillis.mailserver.server.*;

/**
 * This is our controller class. It receives events (HTTP GET and POST) from the 
 * view, pulling the necessary arguments from the HTTP request and building an 
 * EmailImpl object.
 * 
 * The Controller then accesses the Model via the MailManager Facade, requesting
 * an IMailHandler object, to which the EmailImpl is passed.
 * 
 * The Model is completely agnostic to the nature of the client.
 * 
 * Once the Model consumes the EmailImpl and processes it, the result of that 
 * processing is returned to the Controller, which then passes the result to the
 * view.
 * @author Mark Willis
 *
 */

// Map this servlet to the /mail URL
@WebServlet(
        name = "mailServlet",
        urlPatterns = {"/"},
        loadOnStartup = 1
)

// configure file upload for attachments
// Up to 5MB in memory
// Over 5MB, commit to disk at default temp location
// Throttle total request size at an arbitrary 40MB
@MultipartConfig(
		fileSizeThreshold =  5_242_880,  // 5MB
		maxFileSize       = 20_971_520L, //20MB
		maxRequestSize    = 41_943_040L  //40MB
)
public class MailServlet extends HttpServlet {
	
	/**
	 * Handle HTTP GET by forwarding the user to the Mail Client JSP
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.getRequestDispatcher("/WEB-INF/jsp/view/MailClientJSP.jsp").forward(request, response);
	}
	
	/**
	 * Show time.  The user has requested to send an email.  Handle the HTTP POST
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		SendResult result = null;
		try
		{
		    result = sendEmail(request);
		}
		catch (Throwable thr){
			result = new SendResult(false, "Error sending mail: " + thr.getMessage());
		}
		finally{
			request.setAttribute("result", result);
			request.getRequestDispatcher("/WEB-INF/jsp/view/Results.jsp").forward(request, response);
		}
	}
	
	/**
	 * Attempt to send an email from the parameters present on the HTTP request
	 * @param request
	 * @throws ServletException
	 * @throws IOException
	 */
	private SendResult sendEmail(HttpServletRequest request) throws ServletException, IOException{
		IEmail myEmail = createEmail(request);
		
		return MailManager.getInstance().sendMail(myEmail);
	}
	
	/**
	 * Parse the request to build an instance of IEmail
	 * @param request
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	private IEmail createEmail(HttpServletRequest request) throws ServletException, IOException{
		IEmail myEmail = EmailFactory.getEmail();
		
		EmailFactory.setSender(myEmail, "joe.bloggs@hotmail.com");
		EmailFactory.setRecipients(myEmail, request.getParameter("to"), IEmail.Visibility.TO);
		EmailFactory.setRecipients(myEmail, request.getParameter("cc"), IEmail.Visibility.CC);
		EmailFactory.setRecipients(myEmail, request.getParameter("bcc"), IEmail.Visibility.BCC);
		EmailFactory.setSubject(myEmail, request.getParameter("subject"));
		EmailFactory.setBody(myEmail, request.getParameter("body"));
		
		Part filePart = request.getPart("attachment");
        if(filePart != null && filePart.getSize() > 0){
        	byte[] rawData = downloadAttachment(filePart);
        	
        	if (rawData != null){
        		EmailFactory.addAttachment(myEmail,
        				                   filePart.getSubmittedFileName(), 
        				                   rawData, filePart.getContentType());
        	}
        }
				
		return myEmail;
	}
	
	/**
	 * An attachment has been detected.  Read it in
	 * @param filePart
	 * @return
	 * @throws IOException
	 */
	private byte[] downloadAttachment (Part filePart)throws IOException{
		
		InputStream inputStream = filePart.getInputStream();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        int read;
        final byte[] bytes = new byte[1024];

        while((read = inputStream.read(bytes)) != -1){
            outputStream.write(bytes, 0, read);
        }

        return outputStream.toByteArray();
	}
}