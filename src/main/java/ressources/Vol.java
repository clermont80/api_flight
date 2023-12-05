package ressources;

import jakarta.enterprise.inject.build.compatible.spi.Validation;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import jakarta.validation.Validator;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Path("/vols")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Vol extends Generic
{
    @Inject
    Validator validator;

    @Inject
    repositories.Vol repository;

    @Inject
    repositories.Reservation repositoryResa;


    @GET
    public Response getVols(@QueryParam("destination") String destination)
    {
        List<beans.Vol> list = null;

        if(StringUtils.isNoneBlank(destination))
        {
            list = repository.findByDest(destination);
        }
        
        return getOr404(list);
    }

    @GET
    @Path("/{id}")
    public Response getVolById(@PathParam("id") Long id) //path param
    {
        var list = repository.findByVol(id);
        return getOr404(list);
    }

    @POST
    @Transactional
    public Response createVol(beans.Vol vol)
    {
        var violation = validator.validate(vol);

        if(!violation.isEmpty())
        {
            return Response.status(400).entity(new ErrorWrapper(violation)).build();
        }
        try
        {
            repository.persistAndFlush(vol);
            return  Response.ok().status(201).build();
        }catch(PersistenceException e)
        {
            return Response.serverError().entity(new ErrorWrapper("Erreur lors de l'enregistrement")).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteVol(@PathParam("id") Integer id)
    {
        try
        {
            repository.deleteById(id.longValue());

            var listLinkedResa = repositoryResa.findReservationByFlightId(id);

            for(var resa : listLinkedResa)
            {
                repositoryResa.deleteResaById(resa.getId().longValue());
            }

            return Response.ok().status(201).build();
        }
        catch(PersistenceException e)
        {
            return Response.serverError().entity(new ErrorWrapper("Erreur lors de la suppression")).build();
        }

    }

}
