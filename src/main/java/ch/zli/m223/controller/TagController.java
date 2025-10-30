package ch.zli.m223.controller;

import java.util.List;

import ch.zli.m223.dto.TagDTO;
import ch.zli.m223.service.TagService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/tags")
@Tag(name = "Tags", description = "Handling of tags")
public class TagController {
  @Inject
  TagService tagService;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(summary = "Index all Tags.", description = "Returns a list of all tags.")
  public List<TagDTO> index () {
    return tagService.findAll();
  }

  @POST
  @Path("/create")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Operation(summary = "Creates a new Tags.", description = "Creates a new tags and returns the newly added tags.")
  public TagDTO create (TagDTO tagDTO) {
    return tagService.createEntry(tagDTO);
  }

  @PUT
  @Path("/update")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Operation(summary = "Updates an existing Tags.", description = "Updates an existing tags and returns the status of the update.")
  public boolean update (TagDTO tagDTO) {
    return tagService.updateEntry(tagDTO);
  }

  @DELETE
  @Path("/delete/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public boolean delete(@PathParam("id") Long id) {
    return tagService.deleteEntry(id);
  }
}
