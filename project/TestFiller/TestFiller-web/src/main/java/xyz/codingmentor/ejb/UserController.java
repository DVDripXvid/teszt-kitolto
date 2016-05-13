package xyz.codingmentor.ejb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.codingmentor.ejb.facade.RoleFacade;
import xyz.codingmentor.ejb.facade.UserFacade;
import xyz.codingmentor.entity.User;
import xyz.codingmentor.role.RoleName;

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
    
    public void acceptUser(Long id){
        User user = userFacade.read(User.class, id);
        LOGGER.info(user.toString());
        user.setAccepted(true);
        userFacade.update(user);
    }
    
    public List<User> getNonAcceptedUsers(){
        return userFacade.getNonAcceptedUsers();
    }

    

}
