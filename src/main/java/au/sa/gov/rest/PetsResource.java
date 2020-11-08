package au.sa.gov.rest;

import io.quarkus.panache.common.Sort;
import org.h2.util.StringUtils;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/pets")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
@ApplicationScoped
public class PetsResource {
    public PetsResource() {
    }

    @GET
    public List<Pet> list() {
        return Pet.listAll(Sort.by("name"));
    }

    @GET
    @Path("breeds")
    public List<String> getBreeds() {
        return Pet.getListOfBreeds();
    }

    @POST
    @Transactional
    public Response add(Pet pet) {
        if (StringUtils.isNullOrEmpty(pet.name)) {
            throw new WebApplicationException("Name cannot be empty");
        }
        pet.persist();
        return Response.ok().status(201).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response update(long id, Pet pet) {
        if (StringUtils.isNullOrEmpty(pet.name)) {
            throw new WebApplicationException("Name cannot be empty");
        }
        Pet stored_pet = Pet.findById(id);

        if (stored_pet == null) {
            throw new WebApplicationException("Pet with id " + id + " not found.");
        }

        stored_pet.breed = pet.breed;
        stored_pet.name = pet.name;
        stored_pet.species = pet.species;

        return Response.ok().status(201).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response delete(long id) {
        Pet pet = Pet.findById(id);
        if (pet == null) {
            throw new WebApplicationException("Pet with id " + id + " not found.");
        }
        pet.delete();
        return Response.ok().status(204).build();
    }

}
