package org.unibl.etf.traveltickets.db.dao;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import org.unibl.etf.traveltickets.db.dto.Ticket;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

public class TicketDao {
	private static File file=null;
	private String dbLocation;
	public TicketDao(String dbName) {
		dbLocation=dbName;
		if(file==null) {
			file=new File(dbLocation);
		}
		if(file.exists()) {
		deleteAll();
		}
		Ticket ticket = new Ticket("Belgrade", "Bus", "40.0");
		insert(ticket);
		ticket = new Ticket("Belgrade", "Train", "30.0");
		insert(ticket);
		ticket = new Ticket("Belgrade", "Airplane", "100.0");
		insert(ticket);
		ticket = new Ticket("Sarajevo", "BUS", "50.0");
		insert(ticket);
	}
	
	public synchronized void insert(Ticket ticket) {
		ObjectContainer db = Db4oEmbedded.openFile(dbLocation);
		db.store(ticket);
		db.close();
	}

	public synchronized void deleteAll() {
		ObjectContainer db = Db4oEmbedded.openFile(dbLocation);
		for (Object obj : db.queryByExample(new Ticket(null, null, null))) {
			db.delete(obj);
		}
		db.close();
	}

	public synchronized List<Ticket> getAllTickets() {
		ObjectContainer db = Db4oEmbedded.openFile(dbLocation);
		ObjectSet<Object> set = db.queryByExample(new Ticket(null, null, null));
		Ticket[] array = set.toArray(new Ticket[set.size()]);
		db.close();

		return Arrays.asList(array);
	}
	
	public synchronized Ticket get(String destination, String type) {
		List<Ticket> list = getAllTickets();
		for(Ticket t: list) {
			if(t.getDestination().equals(destination) && t.getTransportType().equals(type)) 
				return t;
		}
		return null;
	}
	
	
}
