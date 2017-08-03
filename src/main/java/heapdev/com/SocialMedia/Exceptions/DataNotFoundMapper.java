package heapdev.com.SocialMedia.Exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import heapdev.com.SocialMedia.Models.ErrorMessage;

@Provider
public class DataNotFoundMapper implements ExceptionMapper<CustomizedException> {

	@Override
	public Response toResponse(CustomizedException exception) {
		ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), 404,
				"http://heapdev.com/errors/documentations/");
		return Response.status(Status.NOT_FOUND).entity(errorMessage).build();
	}
}
