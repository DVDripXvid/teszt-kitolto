package xyz.codingmentor.ejb;

import java.io.IOException;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.codingmentor.ejb.facade.EntityFacade;
import xyz.codingmentor.entity.User;

@ManagedBean
@SessionScoped
public class PasswordController implements Serializable {

    private final ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
    private static final Logger LOGGER = LoggerFactory.getLogger(UserProfileModification.class);
    
    @EJB
    private EntityFacade entityFacade;
    private User activeUser;
    
    public void initPage() {
        activeUser = entityFacade.namedQueryOneParam("USERS.findByEmail", User.class, "email", ec.getRemoteUser()).get(0);
        try {
            ec.redirect(ec.getApplicationContextPath() + "/faces/user/changePassword.xhtml");
        } catch (IOException ex) {
            LOGGER.info(ex.getMessage());
        }
    }
}
