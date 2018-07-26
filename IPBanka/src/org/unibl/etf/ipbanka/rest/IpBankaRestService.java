package org.unibl.etf.ipbanka.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.unibl.etf.ipbanka.util.ServiceUtility;

@Path("/")
public class IpBankaRestService {
	@Path("/check/{mail}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response doesUserExists(@PathParam("mail") String mail ) {
		return Response.ok(ServiceUtility.doesExist(mail).toString()).build();
	}
	
	
}
