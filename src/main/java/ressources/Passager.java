package ressources;

import jakarta.enterprise.inject.build.compatible.spi.Validation;
import jakarta.inject.Inject;
import jakarta.persistence.Entity;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import jakarta.validation.Validator;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Path("/passagers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Passager extends Generic
{
    @Inject
    Validator validator;

    @Inject
    repositories.Passager repository;

    @GET
    @Path("/{id}")
    public Response getVolById(@PathParam("id") Long id) //path param
    {
        var list = repository.findByPassengerId(id);
        return getOr404(list);
    }

    @PUT
    @Transactional
    @Path("/{id}")
    public Response updatePassager(@PathParam("id")Long id, beans.Passager passager)
    {
        try
        {
            repository.modifyPassenger(id,passager);
            return  Response.ok().status(204).build();
        }
        catch(PersistenceException e)
        {
            return Response.serverError().entity(new ErrorWrapper("Erreur lors de l'enregistrement")).build();
        }
    }

}