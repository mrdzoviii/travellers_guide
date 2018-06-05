package org.unibl.etf.traveltickets.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
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

public class MailUtilities {
	public static Boolean sendMail(String pdfPath, String mailTo) {
		final String username = "pantelija.etfbl@gmail.com"; // props file
		final String password = "pantelija123"; // propsfile

		Properties props = new Properties();
		props.put("mail.smtp.auth", true);
		props.put("mail.smtp.starttls.enable", true);
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("ticket.service@gmail.com","IP Ticket Service"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailTo));
			message.setSubject("Ticket");
			

			BodyPart messageBodyPart = new MimeBodyPart();
			BodyPart plainText=new MimeBodyPart();
			plainText.setText("PDF version of ticket is placed in attachement");
			Multipart multipart = new MimeMultipart();

			messageBodyPart = new MimeBodyPart();
			String fileName = "ticket.pdf";
			File pdf=new File(pdfPath);
			pdf.deleteOnExit();
			DataSource source = new FileDataSource(pdf);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(fileName);
			multipart.addBodyPart(plainText);
			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);

			Transport.send(message);
			File toDelete=new File(pdfPath);
			if(toDelete.exists()) {
				System.out.println((toDelete.delete()==true)?"Document deleted":"Document not deleted");
			}
			return true;

		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

}
