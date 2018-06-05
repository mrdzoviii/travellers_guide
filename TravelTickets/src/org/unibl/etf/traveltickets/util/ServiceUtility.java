package org.unibl.etf.traveltickets.util;

import java.util.List;

import org.unibl.etf.traveltickets.db.dao.TicketMain;
import org.unibl.etf.traveltickets.db.dto.Ticket;

public class ServiceUtility {
	
	public static List<Ticket> getByDestination(String destination){
		Ticket criteria=new Ticket(destination, null, null);
		TicketMain ticketDb=TicketMain.getTicketMain();
		return ticketDb.selectByTicket(criteria);
	}
	public static Boolean buyTicket(String name,String surname,String mail,String date,String destination,String type) {
		String pdf= PdfDocument.makePdf(name, surname, date, destination,type);
		return MailUtilities.sendMail(pdf, mail);
	}
}
