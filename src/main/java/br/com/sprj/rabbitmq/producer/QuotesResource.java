package br.com.sprj.rabbitmq.producer;

import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import br.com.sprj.rabbitmq.model.Quote;
import io.smallrye.mutiny.Multi;

@Path("/quotes")
public class QuotesResource {

	@Channel("quote-requests")
	Emitter<String> quoteRequestEmmiter;
	
	@Channel("quotes")
	Multi<Quote> quotes;
	
	@POST
	@Path("/request")
	@Produces(MediaType.TEXT_PLAIN)
	public String createRequest() {
		UUID uuid = UUID.randomUUID();
		quoteRequestEmmiter.send(uuid.toString());
		return uuid.toString();
	}
	
	@GET
	@Produces(MediaType.SERVER_SENT_EVENTS)
	public Multi<Quote> stream() {
		return quotes;
	}

}