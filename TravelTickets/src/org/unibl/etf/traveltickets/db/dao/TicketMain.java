package org.unibl.etf.traveltickets.db.dao;

import java.util.ArrayList;
import java.util.List;

import org.unibl.etf.traveltickets.db.dto.Ticket;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

public class TicketMain {
	private static TicketMain reference;
	private static final String dbName="ticket.oodb";
	public static TicketMain getTicketMain() {
		if(reference==null) {
			reference=new TicketMain();
			Ticket example=new Ticket(null, null, null);
			ObjectContainer db = Db4oEmbedded.openFile(dbName);
			ObjectSet<Ticket> driveSet = db.queryByExample(example);
			while (driveSet.hasNext()) {
				db.delete(driveSet.next());
			}
			Ticket []tickets= {new Ticket("Beograd", Ticket.AIRPLANE, "75.00"),new Ticket("Beograd", Ticket.BUS, "54.00"),
					new Ticket("Sarajevo",Ticket.TRAIN,"25.00"),new Ticket("Doboj",Ticket.TRAIN,"20.00"),
					new Ticket("Gradiska",Ticket.BUS,"10.00")};
			for(Ticket t:tickets) {
				db.store(t);
			}
			db.close();
			
		}
		return reference;
	}
	

	public synchronized List<Ticket> selectByTicket(Ticket t) {
		List<Ticket> tickets = new ArrayList<>();
		ObjectContainer db = Db4oEmbedded.openFile(dbName);
		ObjectSet<Ticket> driveSet = db.queryByExample(t);
		while (driveSet.hasNext()) {
			tickets.add(driveSet.next());
		}
		db.close();
		return tickets;
	}


	private TicketMain() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
