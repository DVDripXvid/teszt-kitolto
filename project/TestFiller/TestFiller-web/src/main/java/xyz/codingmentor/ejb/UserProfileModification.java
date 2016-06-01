package xyz.codingmentor.ejb;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.interceptor.Interceptors;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.codingmentor.ejb.facade.EntityFacade;
import xyz.codingmentor.entity.User;
import xyz.codingmentor.interceptor.LoggerInterceptor;
import xyz.codingmentor.role.RoleName;

@ManagedBean
@SessionScoped
@Interceptors({LoggerInterceptor.class})
public class UserProfileModification implements Serializable {

    private final ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
    private static final Logger LOGGER = LoggerFactory.getLogger(UserProfileModification.class);

    @EJB
    private EntityFacade entityFacade;
    private User activeUser;
    private User switchUser;
    private UploadedFile uploadPicture;
    private boolean wantToUpload = false;

    public boolean isWantToUpload() {
        return wantToUpload;
    }

    public void setWantToUpload(boolean wantToUpload) {
        this.wantToUpload = wantToUpload;
    }

    public UploadedFile getUploadPicture() {
        return uploadPicture;
    }

    public void setUploadPicture(UploadedFile uploadPicture) {
        this.uploadPicture = uploadPicture;
    }

    public User getSwitchUser() {
        return switchUser;
    }

    public void setSwitchUser(User switchUser) {
        this.switchUser = switchUser;
    }

    public void modifyProfile() {
        activeUser = entityFacade.namedQueryOneParam("USERS.findByEmail", User.class, "email", ec.getRemoteUser()).get(0);
        switchUser = activeUser;
        try {
            ec.redirect(ec.getApplicationContextPath() + "/faces/userProfile.xhtml");
        } catch (IOException ex) {
            LOGGER.info(ex.getMessage());
        }
    }

    public StreamedContent getImage() {
        return new DefaultStreamedContent(new ByteArrayInputStream(switchUser.getImage()));
    }

    public boolean userHaveProfilePicture() {
        return switchUser.getImage() != null;
    }

    public void modifyProfilePicture() {
        wantToUpload = true;
    }

    public void uploadNewPicture() {
        if (uploadPicture != null) {
            switchUser.setImage(uploadPicture.getContents());
            uploadPicture = null;
        }

        wantToUpload = false;
    }

    public boolean showUploadPictureButton() {
        return !wantToUpload;
    }

    public void save() {
        activeUser = switchUser;
        entityFacade.update(activeUser);

        String rolePath = "";
        if (ec.isUserInRole(RoleName.ADMIN)) {
            rolePath = "admin";
        } else if (ec.isUserInRole(RoleName.TEACHER)) {
            rolePath = "teacher";
        } else if (ec.isUserInRole(RoleName.STUDENT)) {
            rolePath = "student";
        }
        try {
            ec.redirect(ec.getApplicationContextPath() + "/faces/" + rolePath + "/index.xhtml");
        } catch (IOException ex) {
            LOGGER.info(ex.getMessage());
        }
    }

    public void cancel() {
        String rolePath = "";
        if (ec.isUserInRole(RoleName.ADMIN)) {
            rolePath = "admin";
        } else if (ec.isUserInRole(RoleName.TEACHER)) {
            rolePath = "teacher";
        } else if (ec.isUserInRole(RoleName.STUDENT)) {
            rolePath = "student";
        }
        try {
            ec.redirect(ec.getApplicationContextPath() + "/faces/" + rolePath + "/index.xhtml");
        } catch (IOException ex) {
            LOGGER.info(ex.getMessage());
        }
    }
}
