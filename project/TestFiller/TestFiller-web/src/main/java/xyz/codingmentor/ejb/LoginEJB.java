package xyz.codingmentor.ejb;

import java.io.IOException;
import java.io.Serializable;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
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
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        String rolePath = "";
        if(ec.isUserInRole(RoleName.ADMIN)){
            rolePath = "admin";
        }
        else if(ec.isUserInRole(RoleName.TEACHER)){
            rolePath = "teacher";
        }
        else if(ec.isUserInRole(RoleName.STUDENT)){
            rolePath = "student";
        }
        try {
            ec.redirect(ec.getApplicationContextPath() + "/faces/" + rolePath + "/index.xhtml");
        } catch (IOException ex) {
            LOGGER.info(ex.getMessage());
        }
    }
    
    public String logout(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "home";
    }
    
    public String getStatisticsPath(){
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return "http://" + request.getServerName() + ":" + request.getServerPort() + "/TestStat";
    }
}
