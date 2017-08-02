package heapdev.com.SocialMedia.Resources;

import java.net.URI;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import heapdev.com.SocialMedia.Models.Comment;
import heapdev.com.SocialMedia.Services.CommentService;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CommentResource {

	CommentService commentService = new CommentService();

	@GET
	public List<Comment> getAllComments(@PathParam("messageId") long messageId) {
		return commentService.getAllComments(messageId);
	}

//	@GET
//	@Path("{commentId}")
//	public Response getCommentWithCommentId(@PathParam("messageId") long messageId,
//			@PathParam("commentId") long commentId, @Context UriInfo uriInfo) {
//		Comment comment = commentService.getCommentById(messageId, commentId);
//		URI selfUri = uriInfo.getBaseUriBuilder().path(getCommentResource())
//	}

}
