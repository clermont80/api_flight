package repositories;

import beans.Reservation;
import beans.Vol;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.inject.Model;

import java.util.List;

@Model
public class Passager implements PanacheRepositoryBase<beans.Passager,Long>
{
    public beans.Passager findByPassengerId(Long idParam)
    {
        return findById(idParam);
    }

    public boolean modifyPassenger(Long idParam,beans.Passager passager)
    {
        beans.Passager oldPassager = findByPassengerId(idParam);

        if(oldPassager != null && passager != null)
        {
            oldPassager.setSurname(passager.getSurname());
            oldPassager.setFirstname(passager.getFirstname());
            oldPassager.setEmail_address(passager.getEmail_address());

            return true;
        }

        return false;
    }
}
