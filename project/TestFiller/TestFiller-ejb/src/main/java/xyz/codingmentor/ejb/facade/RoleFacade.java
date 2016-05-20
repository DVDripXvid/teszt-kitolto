package xyz.codingmentor.ejb.facade;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import xyz.codingmentor.entity.Role;

/**
 *
 * @author Olivér
 */
@Stateless
public class RoleFacade extends EntityFacade{

    public Role findRole(String name){
        TypedQuery<Role> query = entityManager.createNamedQuery("Role.ByName", Role.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }
    
    public List<Role> findAll(){
        return entityManager.createNamedQuery("Role.findAll", Role.class).getResultList();
    }
    
}
