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

@Path("/avions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Avion extends Generic
{
    @Inject
    Validator validator;

    @Inject
    repositories.Avion repository;


    @GET

    public Response getAvions(@QueryParam("operator") String operator)
    {
        List<beans.Avion> list;

        if(StringUtils.isNoneBlank(operator))
        {
            list = repository.findByOperator(operator);
        }
        else
        {
            list = repository.listAll();
        }

        return getOr404(list);
    }

    @GET
    @Path("/{id}")
    public Response getAvionsById(@PathParam("id") Long id) //path param
    {
        var list = repository.findByIdOptional(id).orElse(null); //optional c'est quand on sait qu'on a pas forcément de réponse -> donc pas de catch
        return getOr404(list);
    }

    @POST
    @Transactional
    public Response createAvion(beans.Avion avion)
    {
        var violation = validator.validate(avion);

        if(violation.isEmpty())
        {
            return Response.status(400).entity(new ErrorWrapper(violation)).build();
        }
        try
        {
            repository.persistAndFlush(avion);
            return  Response.ok().status(201).build();
        }catch(PersistenceException e) {
            return Response.serverError().entity(new ErrorWrapper("Erreur lors de l'enregistrement")).build();
        }
    }

}


