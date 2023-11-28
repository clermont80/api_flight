package repositories;
import java.util.List;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.inject.Model;

@Model
public class Reservation implements PanacheRepositoryBase<beans.Reservation,Long>
{
    //par défaut Panache gère déjà le select *

    public List<beans.Reservation> findReservationByVol(Long idVolParam)
    {
        return find("plane_id",idVolParam).list(); //ici operator c'est la propriété java
    }

    //insert c'est la méthode persist
}
