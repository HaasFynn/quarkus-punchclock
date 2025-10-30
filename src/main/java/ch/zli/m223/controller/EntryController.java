package ch.zli.m223.controller;

import java.util.List;

import ch.zli.m223.dto.EntryDTO;
import ch.zli.m223.service.EntryService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/entries")
@Tag(name = "Entries", description = "Handling of entries")
public class EntryController {

  EntryService entryService;

  @Inject
  public EntryController (EntryService entryService) {
    this.entryService = entryService;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(summary = "Index all Entries.", description = "Returns a list of all entries.")
  public List<EntryDTO> index () {
    return entryService.findAll();
  }

  @POST
  @Path("/create")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Operation(summary = "Creates a new entry.", description = "Creates a new entry and returns the newly added entry.")
  public EntryDTO create (EntryDTO entryDTO) {
    return entryService.createEntry(entryDTO);
  }

  @PUT
  @Path("/update")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Operation(summary = "Updates an existing entry.", description = "Updates an existing entry and returns the status of the update.")
  public boolean update (EntryDTO entryDTO) {
    return entryService.updateEntry(entryDTO);
  }

  @DELETE
  @Path("/delete/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public boolean delete (@PathParam("id") Long id) {
    return entryService.deleteEntry(id);
  }

  @GET
  @Path("/statistics/time-summaries")
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(summary = "Returns sum of worked time.", description = "Returns a summary of time worked on a certain day.")
  public String timeSummaries (@QueryParam("date") String date) {
    return entryService.getTimeSummaries(new java.util.Date(java.sql.Date.valueOf(date).getTime()));
  }

}
