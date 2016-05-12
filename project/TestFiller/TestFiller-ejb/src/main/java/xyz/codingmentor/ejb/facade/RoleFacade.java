package xyz.codingmentor.ejb.facade;

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
    
}
