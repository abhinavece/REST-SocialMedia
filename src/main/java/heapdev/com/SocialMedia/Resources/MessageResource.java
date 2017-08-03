package heapdev.com.SocialMedia.Resources;

import java.net.URI;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import heapdev.com.SocialMedia.Models.Message;
import heapdev.com.SocialMedia.Services.MessageService;

@Path("messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MessageResource {

	public static final Logger logger = LoggerFactory.getLogger(MessageResource.class);

	// @GET
	// @Path("/test")
	// @Produces(MediaType.TEXT_PLAIN)
	// public String test() {
	// logger.info("==> Test passed !!");
	// return "message test succeeded";
	// }

	private static MessageService messageService = new MessageService();

	@GET
	@Path("/")
	public List<Message> getAllMessages(@QueryParam("year") int year, 
										@QueryParam("start") int start,
										@QueryParam("size") int size) {
		if (year > 0) {
			return messageService.getAllMessagesByYear(year);
		}
		if (start >= 0 && size > 0) {
			return messageService.getAllMessagesPaginated(start, size);
		}
		return messageService.getAllMessages();
	}

	@GET
	@Path("/{messageId}")
	public Response getMessageWithId(@PathParam("messageId") Long id, @Context UriInfo uriInfo) {
		Message message = messageService.getMessageWithId(id);
		URI uri = uriInfo.getBaseUriBuilder().path(MessageResource.class).path(id.toString()).build();
		return Response.ok(uri).entity(message).build();
	}

	@POST
	@Path("/")
	public Response createMessage(Message message, @Context UriInfo uriInfo) {
		Message newMessage = messageService.createMessage(message);

		URI selfUri = getSelfuri(uriInfo, newMessage);
		URI profileUri = getProfileUri(message, uriInfo);

		message.addLink(selfUri.toString(), "self");
		message.addLink(profileUri.toString(), "profile");

		return Response.created(selfUri).entity(newMessage).build();
	}

	private URI getProfileUri(Message message, UriInfo uriInfo) {
		URI profileUri = uriInfo.getBaseUriBuilder().path(ProfileResource.class).path(message.getAuthor()).build();
		return profileUri;
	}

	private URI getSelfuri(UriInfo uriInfo, Message newMessage) {
		URI selfUri = uriInfo.getBaseUriBuilder().path(MessageResource.class)
				.path(((Long) newMessage.getId()).toString()).build();
		return selfUri;
	}

	@PUT
	@Path("/{messageId}")
	public Response updateMessage(@PathParam("messageId") Long id, Message message, @Context UriInfo uriInfo) {
		URI selfUri = uriInfo.getBaseUriBuilder().path(MessageResource.class).path(id.toString()).build();
		URI profileUri = uriInfo.getBaseUriBuilder().path(ProfileResource.class).path(message.getAuthor()).build();
		message.setId(id);
		message.addLink(selfUri.toString(), "self");
		message.addLink(profileUri.toString(), "profile");

		Message updatedMessage = messageService.updateMessage(message);
		return Response.created(selfUri).entity(updatedMessage).build();
	}

	@DELETE
	@Path("/{messageId}")
	public Response deleteMessage(@PathParam("messageId") long id) {
		Message deletedMessage = messageService.deleteMessage(id);
		return Response.accepted(deletedMessage).build();
	}

	@Path("/{messageId}/comments")
	public CommentResource getCommentResource() {
		return new CommentResource();
	}

}
