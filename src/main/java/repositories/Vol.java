package repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.inject.Model;

import java.util.List;

@Model
public class Vol implements PanacheRepositoryBase<beans.Vol,Long>
{
    //par défaut Panache gère déjà le select *

    public List<beans.Vol> findByDest(String operatorParam)
    {
        return find("destination",operatorParam).list(); //ici operator c'est la propriété java
    }

    public List<beans.Vol> findByVol(Long operatorParam)
    {
        return find("id",operatorParam).list();
    }

    //insert c'est la méthode persist

    public boolean deleteVol(Long operatorParam)
    {
        return deleteById(operatorParam);
    }
}