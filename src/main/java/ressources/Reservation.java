package ressources;

import beans.Passager;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import jakarta.validation.Validator;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import repositories.Avion;

import java.util.List;

@Path("/reservations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Reservation extends Generic
{
    @Inject
    Validator validator;

    @Inject
    Avion repositoryAvion;

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
    @Path("/{number}")
    public Response createReservation(@PathParam("number") String number,beans.Passager passager)
    {
        try
        {
            if(repositoryVol.findByNumber(number) != null) //on vérifie que le vol existe déjà
            {
                //vérification du nombre de places dans l'avion
                var volentity = repositoryVol.findByNumber(number);
                var idvol = volentity.get(0).getId();
                var idavion = volentity.get(0).getPlane_id();
                beans.Avion avion = repositoryAvion.getAvionById(idavion);

                var listallresa = repository.findReservationByFlightId(idvol);
                var size = listallresa.size();
                var capac = avion.getCapacity();

                if(listallresa.size() == avion.getCapacity()) //plus de place
                {
                    String message = "capacité de l avion atteinte";
                    return Response.status(401).entity(message).build();
                }

                //création du passager s'il n'existe pas déjà
                var  list = repositoryPassager.find("email_address", passager.getEmail_address());

                if(list.count() == 0) //le passager n'existe pas
                {
                    beans.Passager newpassager = new Passager();

                    newpassager.setSurname(passager.getSurname());
                    newpassager.setFirstname(passager.getFirstname());
                    newpassager.setEmail_address(passager.getEmail_address());

                    repositoryPassager.persistAndFlush(newpassager); //sauvegarde du nouveau passager

                    //faut aussi créer la reservation

                    beans.Reservation newreservation = new beans.Reservation();
                    newreservation.setFlight_id(idvol);

                    var listentity = repositoryPassager.find("email_address",passager.getEmail_address());
                    var mailaddress = listentity.firstResult().getEmail_address();

                    var persistpassager = repositoryPassager.find("email_address",mailaddress);
                    newreservation.setPassenger_id(persistpassager.firstResult().getId());

                    var caca = newreservation.getFlight_id();
                    var prout = newreservation.getPassenger_id();

                    //le problème est surement dans getReservationByVolId

                    repository.persist(newreservation);
                }
            }
            return  Response.ok().status(201).build();

        }catch(PersistenceException e) {
            return Response.serverError().entity(new ErrorWrapper("Erreur lors de l'enregistrement")).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteResa(@PathParam("id") Long id)
    {
        try
        {
            repository.deleteResaById(id);
            return Response.ok().status(204).build();
        }
        catch(PersistenceException e)
        {
            return Response.serverError().entity(new ErrorWrapper("Erreur lors de la suppression")).build();
        }

    }
}
