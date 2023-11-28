package ressources;

import beans.Passager;
import beans.Vol;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import jakarta.validation.Validator;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Path("/reservations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Reservation extends Generic
{
    @Inject
    Validator validator;

    @Inject
    repositories.Reservation repository;

    @Inject
    repositories.Vol repositoryVol;

    @Inject
    repositories.Passager repositoryPassager;

    @GET
    public Response getReservations()
    {
        List<beans.Reservation> list = repository.listAll();

        return getOr404(list);
    }

    @GET
    @Path("/{id}")
    public Response getReservationByVolId(@PathParam("id") Long id) //path param
    {
        var list = repository.findReservationByVol(id);
        return getOr404(list);
    }

    @POST
    @Transactional
    @Path("/{id}")
    public Response createReservation(@PathParam("id") Long id,beans.Passager passager)
    {
        try
        {
            if(repositoryVol.findByVol(id) != null) //on vérifie que le vol existe déjà
            {
                //création du passager s'il n'existe pas déjà

                var entity = repositoryPassager.find("email_address", passager.getEmail_address());

                if(entity == null)
                {
                    beans.Passager newpassager = new Passager();

                    newpassager.setSurname(passager.getSurname());
                    newpassager.setFirstname(passager.getFirstname());
                    newpassager.setEmail_address(passager.getEmail_address());

                    repositoryPassager.persistAndFlush(newpassager); //sauvegarde du nouveau passager

                    //faut aussi créer la reservation
                }
            }
            return  Response.ok().status(201).build();

        }catch(PersistenceException e) {
            return Response.serverError().entity(new ErrorWrapper("Erreur lors de l'enregistrement")).build();
        }
    }
}
