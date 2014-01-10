package com.kailar.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.kailar.constants.MailConstants;


@Controller
@RequestMapping("/mail")
public class MailController {
	private static final Logger logger = Logger.getLogger(MailController.class.getName());

	private Properties props = new Properties();
	private Session session = Session.getDefaultInstance(props, null);	

	@RequestMapping(value= "/autoReply", method = RequestMethod.POST)
	public String automaticMailReply(HttpServletRequest request, 
			@RequestParam(value="name", required=false) String name, 
			@RequestParam(value="description", required=false) String description, 
			@RequestParam(value="email", required=false) String email, HttpServletResponse response) throws IOException{

		/******** E-Mail ********/
		try {
			//Sending automated response to the user!
			sendUserResponseMail(name,email);
			//Sending automated mail to the admin!
			sendMailToAdmin(name,email,description);
			logger.info("Mail sent to : '"+email+"' and admin!");
		} catch (Exception e) {
			//TODO: Handle exceptions
			return "redirect:../";
		}
		/******** E-Mail ********/
		
		//To redirect the request to the index.html
		return "redirect:../";
	}


	private void sendUserResponseMail(String name, String email) throws Exception{
		//TODO: Use velocity templates for this mail
		StringBuilder strBuilder = new StringBuilder();
		if(!name.split(" ")[0].isEmpty()){
			strBuilder.append("Hi "+name.split(" ")[0]+",\n");	
		}else{
			strBuilder.append("Hi "+name+",\n");
		}
		
		strBuilder.append("\t Thank you for contacting us! We will get back to you soon.\n");
		strBuilder.append("\t Have a nice day!!!\n");
		strBuilder.append(MailConstants.ADMIN_SALUTATION);
		strBuilder.append("\n\nNote: This is an automated mail, please do not reply!");
		String msgBody = strBuilder.toString();

		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(MailConstants.KAILAR_HOMES_EMAIL,
				MailConstants.KAILAR_HOMES_NAME));
		msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
				email, name));
		msg.setSubject("Greetings from Kailar Homes!");
		msg.setText(msgBody);
		Transport.send(msg);
	}

	private void sendMailToAdmin(String name, String email, String description) throws Exception{
		//TODO: Use velocity templates for this mail
		StringBuilder strBuilder = new StringBuilder("Hello,");
		strBuilder.append("\n\t Congratulations you have got mail from "+name+".");
		strBuilder.append("\n\t E-mail ID : "+email);
		strBuilder.append("\n\t ############## "+name+" wrote ############## \n\n"+description);
		strBuilder.append("\n"+MailConstants.ADMIN_SALUTATION);
		String msgBody = strBuilder.toString();

		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(MailConstants.KAILAR_HOMES_EMAIL,
				MailConstants.KAILAR_HOMES_NAME));
		msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
				MailConstants.KAILAR_HOMES_EMAIL,
				MailConstants.KAILAR_HOMES_NAME));
		msg.addRecipients(Message.RecipientType.CC, getAdminList());
		msg.setSubject("Message from "+name+"!");
		msg.setText(msgBody);
		Transport.send(msg);
	}

	private Address[] getAdminList() throws UnsupportedEncodingException{
		InternetAddress[] adminArray = new InternetAddress[4];
		adminArray[0] = new InternetAddress("hb.shashidhar@gmail.com", "Shashidhar HB");
		adminArray[3] = new InternetAddress("harikesh85@gmail.com", "Harikesh Halemane");
		adminArray[1] = new InternetAddress("baggubhat@gmail.com", "Bhagavan Kailar Bhat");
		adminArray[2] = new InternetAddress("balish.c@rediffmail.com", "Balish");
		return adminArray;
	}

}
