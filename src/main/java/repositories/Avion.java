package repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.inject.Model;

import java.util.List;

@Model
public class Avion implements PanacheRepositoryBase<beans.Avion,Long>
{
    //par défaut Panache gère déjà le select *

    public List<beans.Avion> findByOperator(String operatorParam)
    {
        return find("operator",operatorParam).list(); //ici operator c'est la propriété java
    }

    //insert c'est la méthode persist
}
