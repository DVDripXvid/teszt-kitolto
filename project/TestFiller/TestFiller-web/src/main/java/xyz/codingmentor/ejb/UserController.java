package xyz.codingmentor.ejb;

import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.facelets.FaceletContext;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.codingmentor.ejb.facade.UserFacade;
import xyz.codingmentor.entity.QueryName;
import xyz.codingmentor.entity.Role;
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
    public void deleteUser(User user){
        List<Role> roles = userFacade.namedQueryOneParam(QueryName.ROLE_findByUser, Role.class,"user",user);
        for(Role r : roles){
            r.getUsers().remove(user);
            userFacade.update(r);
        }
        
        userFacade.delete(User.class, user.getId());
    }
    
    public void changeAdmin(User user){
        if(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser().equals(user.getEmail())){
            addMessage("ERROR!","Cannot modify yourself");
            return;
        }
        if(!user.changeAdmin()){
            addMessage("ERROR!","Student cannot be admin");
        }
        
        userFacade.update(user);
    }
    
    public void addMessage(String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(summary,detail));
    }
}
