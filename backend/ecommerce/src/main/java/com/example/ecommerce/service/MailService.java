package com.example.ecommerce.service;

import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailService {
	private static String from = "chuyendewebproject@gmail.com";
	private static String password = "zcihlgnqjyhvzqcu" + 
			"";

	public static boolean sendMail(String to, String subject, String content, boolean isBill, String bill) {
		Session session = connect();
		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress("raucuqua111@gmail.com"));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			// Set Subject: header field
			message.setSubject(subject);

			// Now set the actual message
//	            message.setText(content,"UTF-8");

			// Create the message part
			BodyPart messageBodyPart = new MimeBodyPart();

			// Now set the actual message
			messageBodyPart.setText(content);

			// Create a multipar message
			Multipart multipart = new MimeMultipart();

			// Set text message part
			multipart.addBodyPart(messageBodyPart);
			if (isBill) {
				// Part two is attachment
				messageBodyPart = new MimeBodyPart();
//	            String filename = "BL0001.pdf";
				File f = new File(bill);
				DataSource source = new FileDataSource(f);
				messageBodyPart.setDataHandler(new DataHandler(source));
//	            messageBodyPart.setFileName(f.filename);
				messageBodyPart.setFileName(f.getName());
				multipart.addBodyPart(messageBodyPart);
			}
			// Send the complete message parts
			message.setContent(multipart, "UTF-8");

//	            System.out.println("sending...");
			// Send message
			Transport.send(message);
			return true;
//	            System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();

		}
		return false;
	}

	public static boolean sendMail(String to, String subject, String content) {
		return sendMail(to, subject, content, false, "");
	}

	public static Session connect() {
		// Assuming you are sending email from through gmails smtp
		String host = "smtp.gmail.com";

		// Get system properties
		Properties properties = System.getProperties();

		// Setup mail server
		properties.put("mail.smtp.host", host);
		// 465, 587
		properties.put("mail.smtp.port", "587");
		// ssl to starttls
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.auth", "true");
//	        InputStream reader = GetConnection.class.getClassLoader().getResourceAsStream("email.properties");
//	        Properties myProp = new Properties();
//	        try {
//	            myProp.load(reader);

//	            System.out.println(myProp.getProperty("email"));
//	            System.out.println(myProp.getProperty("password"));
		// Get the Session object.// and pass username and password
		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

			protected PasswordAuthentication getPasswordAuthentication() {
				// pass: rau1@haha
//	                return new PasswordAuthentication(myProp.getProperty("email"), myProp.getProperty("password"));
				return new PasswordAuthentication(from, password);

			}

		});

		// Used to debug SMTP issues
		session.setDebug(true);
		return session;
//	        } catch (IOException e) {
//	            throw new RuntimeException(e);
//	        }
	}

	public static void main(String[] args) {
//		String email = "huudao805@gmail.com";
		String email = "19130222@st.hcmuaf.edu.vn";
		String content = "xin chao user 123, day la duong link de lay lai mat khau";
		MailService.sendMail(email, "xác nhận lấy mật khẩu từ trang raucuqua jdk 7", content);
		
//		MailService.sendMail(email, "xác nhận lấy mật khẩu từ trang raucuqua jdk 7", content, true,
//				path.getAbsolutePath());
//	    File path = new File("C:\\Users\\huuda\\.jdks\\corretto-11.0.15\\bin\\bill\\BI0014.pdf");
//		System.out.println(path.getAbsolutePath());
	}
}
