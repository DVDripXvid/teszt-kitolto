package xyz.codingmentor.ejb.facade;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import xyz.codingmentor.entity.User;

/**
 *
 * @author Olivér
 */
@Stateless(name = "userFacade")
public class UserFacade extends EntityFacade{
    
    public <T extends User> T getByEmail(String email, Class<T> clazz){
        TypedQuery<T> query = entityManager.createNamedQuery("USERS.findByEmail", clazz);
        query.setParameter("email", email);
        return query.getSingleResult();
    }
    
    public List<User> getNonAcceptedUsers(){
        return entityManager.createNamedQuery("USERS.findNonAccepted", User.class).getResultList();
    }   
    
}
