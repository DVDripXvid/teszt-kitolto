package hu.codingmentor.ejb;

import java.io.Serializable;
import javax.faces.bean.RequestScoped;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Olivér
 */
@Named
@RequestScoped
public class LoginEJB implements Serializable{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginEJB.class);

    public String login(){
        LOGGER.info("pressed");
        return "admin";
    }
}
