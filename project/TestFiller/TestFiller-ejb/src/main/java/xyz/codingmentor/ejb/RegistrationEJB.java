package xyz.codingmentor.ejb;

import javax.ejb.EJB;
import javax.inject.Named;
import xyz.codingmentor.ejb.facade.EntityFacade;
import xyz.codingmentor.entity.User;

/**
 *
 * @author Olivér
 */
@Named
public class RegistrationEJB {    
    
    @EJB
    private EntityFacade facade;
    
    private User user = new User();
    
    public void register(){
        user.setAccepted(false);
        facade.create(user);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
