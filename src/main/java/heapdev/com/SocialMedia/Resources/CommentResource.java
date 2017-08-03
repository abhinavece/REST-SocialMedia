package heapdev.com.SocialMedia.Resources;

import java.net.URI;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
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

	@GET
	@Path("{commentId}")
	public Response getCommentWithCommentId(@PathParam("messageId") Long messageId,
			@PathParam("commentId") Long commentId) {
		Comment gComment = commentService.getCommentById(messageId, commentId);
		return Response.status(Status.OK).entity(gComment).build();
	}
	
	@POST
	public Response createComment(@PathParam("messageId") Long messageId, Comment comment, @Context UriInfo uriInfo) {
		Comment newComment = commentService.createNewComment(messageId, comment);
		
		URI selfUri = uriInfo.getAbsolutePathBuilder()
											.path(CommentResource.class)
											.path(new Long(newComment.getId())
											.toString())
											.build();
		newComment.addLink(selfUri.toString(), "self");
		return Response.created(selfUri).entity(newComment).build();
	}
}
