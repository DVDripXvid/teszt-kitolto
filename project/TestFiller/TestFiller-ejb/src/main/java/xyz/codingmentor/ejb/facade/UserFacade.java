package xyz.codingmentor.ejb.facade;

import java.util.List;
import javax.ejb.Stateless;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import xyz.codingmentor.entity.User;

/**
 *
 * @author Olivér
 */
@Stateless(name = "userFacade")
public class UserFacade extends EntityFacade{

    public List<User> getNonAcceptedUsers(){
        return entityManager.createNamedQuery("USERS.findNonAccepted", User.class).getResultList();
    }
 
    
    
}
