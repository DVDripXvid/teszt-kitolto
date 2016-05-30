package xyz.codingmentor.ejb;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
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
public class UserController implements Serializable {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
    
    private User user;
    @EJB
    private UserFacade userFacade;   
    
    @EJB(name="emailService")
    private EmailService emailService;
    
    @PostConstruct
    public void logHttpData(){
        LOGGER.info("App Context Path: " + FacesContext.getCurrentInstance().getExternalContext().getApplicationContextPath());
        LOGGER.info("Request Context Path: " + FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath());
        LOGGER.info("Request Servlet Path: " + FacesContext.getCurrentInstance().getExternalContext().getRequestServletPath());
        LOGGER.info("Request Server Name: " + FacesContext.getCurrentInstance().getExternalContext().getRequestServerName());
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        LOGGER.info("port: " + request.getServerPort());
        LOGGER.info("http://" + request.getRemoteHost() + ":" + request.getServerPort() + "/TestFiller-web");
    }
    
    public void acceptUser(Long id){
        User user = userFacade.read(User.class, id);
        LOGGER.info(user.toString());
        user.setAccepted(true);
        emailService.sendRegistrationEmail(user, FacesContext.getCurrentInstance().getExternalContext().getApplicationContextPath());
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
    
    public void denyRegistrationRequest(User user){
        emailService.sendEmail(user.getEmail(), "Deny Registration", "Your registration request was denied on TestFiller");
        userFacade.delete(User.class, user.getId());
    }
    
    public String getCurrentUser(){
        user = userFacade.namedQueryOneParam("USERS.findByEmail",User.class, "email",ec.getRemoteUser()).get(0);
        return user.getFirstName() + " " + user.getLastName();
    }
}
