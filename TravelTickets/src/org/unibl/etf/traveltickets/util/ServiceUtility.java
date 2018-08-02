package org.unibl.etf.traveltickets.util;

import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;

import org.unibl.etf.traveltickets.db.dao.TicketDao;
import org.unibl.etf.traveltickets.db.dto.Ticket;

public class ServiceUtility {
	private static final ResourceBundle BUNDLE=ResourceBundle.getBundle("org.unibl.etf.traveltickets.config.TravelTicketsConfig");
	private static final String dbName=BUNDLE.getString("oodb.db.name");

	public static List<Ticket> getAllTickets(ServletContext context) {
		return new TicketDao(context.getRealPath(dbName)).getAllTickets();
	}

	public static Boolean buyTicket(String name, String surname, String mail, String date, String destination,
			String type) {
		String pdf = PdfDocument.makePdf(name, surname, date, destination, type);
		if (pdf != null) {
			MailUtilities.sendMail(pdf, mail);
			return true;
		}
		return false;
	}
}
