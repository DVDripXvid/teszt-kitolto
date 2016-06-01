package xyz.codingmentor.ejb;

import java.io.IOException;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
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
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public User getActiveUser() {
        return activeUser;
    }

    public void setActiveUser(User activeUser) {
        this.activeUser = activeUser;
    }

    public void initPage() {
        activeUser = entityFacade.namedQueryOneParam("USERS.findByEmail", User.class, "email", ec.getRemoteUser()).get(0);
        try {
            ec.redirect(ec.getApplicationContextPath() + "/faces/changePassword.xhtml");
        } catch (IOException ex) {
            LOGGER.info(ex.getMessage());
        }
    }
    
    public void changePassword() {
        if (oldPassword != null && newPassword != null && confirmPassword != null) {
            if (activeUser.getPassword().equals(oldPassword) && newPassword.equals(confirmPassword)) {
                activeUser.setPassword(newPassword);
            }
        }
    }

    public void confirm() {
        changePassword();
        entityFacade.update(activeUser);

        try {
            ec.redirect(ec.getApplicationContextPath() + "/faces/userProfile.xhtml");
        } catch (IOException ex) {
            LOGGER.info(ex.getMessage());
        }
    }

    public void cancel() {
        try {
            ec.redirect(ec.getApplicationContextPath() + "/faces/userProfile.xhtml");
        } catch (IOException ex) {
            LOGGER.info(ex.getMessage());
        }
    }
}
