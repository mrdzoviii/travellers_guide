package org.unibl.etf.traveltickets.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;
import org.unibl.etf.traveltickets.db.dto.Ticket;
import org.unibl.etf.traveltickets.rest.model.Request;
import org.unibl.etf.traveltickets.util.ServiceUtility;


@Path("/rest")
public class TravelTicketsRestService {
	@Path("/destination/{destination}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTickets(@PathParam("destination") String destination) {
		JSONArray array=new JSONArray();
		List<Ticket> tickets=ServiceUtility.getByDestination(destination);
		for(Ticket t:tickets) {
			array.put(new JSONObject(t));
		}
		return Response.ok(array.toString()).build();
	}
	@Path("/buyticket/")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response buyTicket(Request request) {
		return Response.ok(ServiceUtility.buyTicket(request.getName(), request.getSurname(), request.getMail(), 
				request.getDate(), request.getDestination(),request.getTransportType()).toString()).build();
	}
}
