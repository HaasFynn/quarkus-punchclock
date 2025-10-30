package ch.zli.m223.controller;

import java.util.List;

import ch.zli.m223.dto.CategoryDTO;
import ch.zli.m223.service.CategoryService;
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

@Path("/categories")
@Tag(name = "Categories", description = "Handling of categories")
public class CategoryController {

  @Inject
  CategoryService categoryService;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(summary = "Index all Categories.", description = "Returns a list of all categories.")
  public List<CategoryDTO> index () {
    return categoryService.findAll();
  }

  @POST
  @Path("/create")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Operation(summary = "Creates a new category.", description = "Creates a new category and returns the newly added category.")
  public CategoryDTO create (CategoryDTO entryDTO) {
    return categoryService.createEntry(entryDTO);
  }

  @PUT
  @Path("/update")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Operation(summary = "Updates an existing category.", description = "Updates an existing category and returns the status of the update.")
  public boolean update (CategoryDTO entryDTO) {
    return categoryService.updateEntry(entryDTO);
  }

  @DELETE
  @Path("/delete/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public boolean delete(@PathParam("id") Long id) {
    return categoryService.deleteEntry(id);
  }
}
