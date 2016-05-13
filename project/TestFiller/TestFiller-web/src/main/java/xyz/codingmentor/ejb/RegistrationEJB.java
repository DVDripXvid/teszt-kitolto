package xyz.codingmentor.ejb;

import java.io.Serializable;
import java.util.Random;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import xyz.codingmentor.ejb.facade.EntityFacade;
import xyz.codingmentor.entity.User;

/**
 *
 * @author Olivér
 */
@ManagedBean(name="registrationEJB")
@RequestScoped
public class RegistrationEJB implements Serializable{    
    
    @EJB(name="entityFacade")
    private EntityFacade facade;
    
    @EJB(name = "emailService")
    private EmailService emailService;
    
    private User user = new User();
    
    
    public void register(){
        user.setAccepted(false);
        char[] pw = new char[6];
        for(int i=0; i<6; i++){
            pw[i] = (char) (new Random().nextInt(26)+65);
        }
        user.setPassword(String.copyValueOf(pw));
        facade.create(user);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
