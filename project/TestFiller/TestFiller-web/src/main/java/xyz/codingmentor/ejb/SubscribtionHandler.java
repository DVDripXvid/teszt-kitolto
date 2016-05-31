package xyz.codingmentor.ejb;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.interceptor.Interceptors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.codingmentor.ejb.facade.EntityFacade;
import xyz.codingmentor.entity.QueryName;
import xyz.codingmentor.entity.Student;
import xyz.codingmentor.interceptor.LoggerInterceptor;

/**
 *
 * @author Olivér
 */
@ManagedBean
@SessionScoped
@Interceptors({LoggerInterceptor.class})
public class SubscribtionHandler implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubscribtionHandler.class);
    
    @EJB(name = "entityFacade")
    private EntityFacade facade;
    
    public List<Student> getSubscribers(){
        return facade.namedQuery(QueryName.STUDENT_getSubscribers, Student.class);
    }
    
    public void acceptSubscribtion(Student student){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Accepted"));
        student.getCourses().add(student.getSubscribed());
        student.setSubscribed(null);
        LOGGER.info(student.getLastName() + " subscribing...");
        facade.update(student);
    }
    
    public void denySubscribtion(Student student){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Denied"));
        LOGGER.info(student.getLastName() + " unsubscribing...");
        student.setSubscribed(null);
        facade.update(student);
    }
}
