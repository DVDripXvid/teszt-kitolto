package xyz.codingmentor.ejb;

import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.codingmentor.ejb.facade.UserFacade;
import xyz.codingmentor.entity.QueryName;
import xyz.codingmentor.entity.User;

/**
 *
 * @author Olivér
 */
@Named
@SessionScoped
public class UserController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @EJB
    private UserFacade userFacade;   
    
    @EJB(name="emailService")
    private EmailService emailService;
    
    public void acceptUser(Long id){
        User user = userFacade.read(User.class, id);
        LOGGER.info(user.toString());
        user.setAccepted(true);
        emailService.sendRegistrationEmail(user);
        userFacade.update(user);
    }
    
    public List<User> getNonAcceptedUsers(){
        return userFacade.getNonAcceptedUsers();
    }
    
    public List<User> getUsers(){
        return userFacade.namedQuery(QueryName.USERS_findAll, User.class);
    }
    public void deleteUser(long id){
        
        userFacade.delete(User.class, id);
    }
}
