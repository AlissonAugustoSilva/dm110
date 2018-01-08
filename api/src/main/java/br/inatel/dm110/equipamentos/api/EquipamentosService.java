package br.inatel.dm110.equipamentos.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/poller")
public interface EquipamentosService {
	@GET
	@Path("/start/{ip}/{Mask}")
	@Produces(MediaType.TEXT_HTML)
	void scan(@PathParam("ip") String ip, @PathParam("Mask") String mask );
	
	@GET
	@Path("/status/{ip}")
	@Produces(MediaType.TEXT_PLAIN)
	String checkstatus(@PathParam("ip") String ip);

}
