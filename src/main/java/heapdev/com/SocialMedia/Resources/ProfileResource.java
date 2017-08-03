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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import heapdev.com.SocialMedia.Models.Profile;
import heapdev.com.SocialMedia.Services.ProfileServices;


@Path("profiles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProfileResource {

	public static final Logger logger = LoggerFactory.getLogger(ProfileResource.class);

	ProfileServices profileService = new ProfileServices();

	// @GET
	// @Path("/test")
	// @Produces(MediaType.APPLICATION_JSON)
	// public String test() {
	// return "Test Passed";
	// }

	@GET
	public List<Profile> getAllProfiles() throws Exception {
		return profileService.getAllProfiles();
//		return Response.status(200).entity(profileService.getAllProfiles()).build();  => MessageBodyWriter Error
	}

	@GET
	@Path("/{username}")
	public Response getProfile(@PathParam("username") String username, @Context UriInfo uriInfo) throws Exception {

		// URI uri = uriInfo.getAbsolutePathBuilder().build();
		URI uri = getSelfUriForProfile(username, uriInfo);
		Profile profile = profileService.getIndividualProfile(username);
		return Response.ok(uri).entity(profileService.getIndividualProfile(username)).build();
	}

	private URI getSelfUriForProfile(String username, UriInfo uriInfo) {
		URI uri = uriInfo
						.getBaseUriBuilder()
						.path(ProfileResource.class)
						.path(username)
						.build();
		return uri;
	}

	@POST
	public Response createProfile(Profile profile, @Context UriInfo uriInfo) {
		URI uri = uriInfo.getAbsolutePathBuilder().path(profile.getUsername()).build();
		Profile newProfile = profileService.createNewProfile(profile);
		profile.addLink(uri.toString(), "self");
		return Response.created(uri).entity(newProfile).build();
	}

	@PUT
	@Path("/{username}")
	public Response updateProfile(@PathParam("username") String username, Profile profile, @Context UriInfo uriInfo) {
		profile.setUsername(username);
		URI uri = uriInfo.getAbsolutePathBuilder().path(profile.getUsername()).build();
		profile.addLink(uri.toString(), "self");
		Profile updatedProfile = profileService.updateProfile(username, profile);
		return Response.accepted(uri).entity(updatedProfile).build();
	}

	@DELETE
	@Path("/{username}")
	public void deleteResponse(@PathParam("username") String username) {
		profileService.deleteProfile(username);
	}
}
