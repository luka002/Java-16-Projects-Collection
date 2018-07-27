package hr.fer.zemris.jsdemo.rest;

import java.util.List;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * This class enables support for REST.
 * There are three paths tied to this REST.
 * 		1. /gallery/tags - fetches all tags from
 * 			the galley and sends them as JSON format.
 * 		2. /gallery/tags/{tag} - fetches all thumbnails 
 * 			with tag given and sends them as JSON
 * 		3. /gallery/segment/{path} - fetches segment with
 * 			name given and sends it as JSON format
 * 
 * @author Luka Grgić
 */
@Path("/gallery")
public class GalleryRest {

	@Path("/tags")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTags() {
		Set<String> tags = Gallery.getInstance().getTags();
		
		JSONArray tagArray = new JSONArray();
		for(String tag : tags) {
			tagArray.put(tag);
		}
		
		JSONObject result = new JSONObject();
		result.put("tags", tagArray);
		
		return Response.status(Status.OK).entity(result.toString()).build();
	}
	
	@Path("/tags/{tag}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTag(@PathParam("tag") String tag) {
		List<GallerySegment> segments = Gallery.getInstance().getSegmentsWithTag(tag);
		JSONArray jArray = new JSONArray();
		
		for(GallerySegment segment : segments) {
			JSONObject seg = new JSONObject();
			
			seg.put("title", segment.getTitle());
			seg.put("name", "servleti/thumb?thumb=" +  segment.getName());
			
			jArray.put(seg);
		}
		
		JSONObject result = new JSONObject();
		result.put("thumbs", jArray);
		
		return Response.status(Status.OK).entity(result.toString()).build();
	}
	
	@Path("/segment/{name}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSegment(@PathParam("name") String name) {
		GallerySegment segment = Gallery.getInstance().getSegment(name);
		JSONObject seg = new JSONObject();
				
		seg.put("title", segment.getTitle());
		seg.put("name", "servleti/segment?segment=" +  segment.getName());
		
		JSONArray jArray = new JSONArray();
		for(String tag : segment.getTags()) {
			jArray.put(tag);
		}
		
		seg.put("tags", jArray);
		
		return Response.status(Status.OK).entity(seg.toString()).build();
	}
	
}
