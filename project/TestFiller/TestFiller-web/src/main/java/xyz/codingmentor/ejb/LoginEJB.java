package xyz.codingmentor.ejb;

import java.io.IOException;
import java.io.Serializable;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.codingmentor.role.RoleName;

/**
 *
 * @author Olivér
 */
@Named
@RequestScoped
public class LoginEJB implements Serializable{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginEJB.class);

    public void login(){
        String rolePath = "";
        if(FacesContext.getCurrentInstance().getExternalContext().isUserInRole(RoleName.ADMIN)){
            rolePath = "admin";
        }
        if(FacesContext.getCurrentInstance().getExternalContext().isUserInRole(RoleName.TEACHER)){
            rolePath = "teacher";
        }
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("http://localhost:1111/TestFiller-web-1.0-SNAPSHOT/faces/" + rolePath +"/index.xhtml");
        } catch (IOException ex) {
            LOGGER.info(ex.getMessage());
        }
    }
    
    public String logout(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "home";
    }
}
