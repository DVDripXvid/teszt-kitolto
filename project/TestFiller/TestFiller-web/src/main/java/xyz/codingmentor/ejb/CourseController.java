package xyz.codingmentor.ejb;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.codingmentor.ejb.facade.CourseFacade;
import xyz.codingmentor.ejb.facade.EntityFacade;
import xyz.codingmentor.ejb.facade.UserFacade;
import xyz.codingmentor.entity.Course;
import xyz.codingmentor.entity.Student;

@Named
@SessionScoped
public class CourseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CourseController.class);
    
    @EJB
    private CourseFacade courseFacade;
    
    @EJB 
    private UserFacade userFacade;
    
    @EJB
    private EntityFacade entityFacade;
    
    private List<Course> subscribedCourses;
    
    private static final List<String> accordingToSubscribeTypes = new ArrayList<>();
    
    private String selectedSubscribedType;

    public String getSelectedSubscribedType() {
        return selectedSubscribedType;
    }

    public static List<String> getAccordingToSubscribeTypes() {
        return accordingToSubscribeTypes;
    }
    
    static {
        accordingToSubscribeTypes.add("ALL");
        accordingToSubscribeTypes.add("SUBSCRIBED");
        accordingToSubscribeTypes.add("NOT SUBSCRIBED");
    }
    
    public List<Course> getActiveCourses(){
        return courseFacade.getActiveCourses();
    }
    
    public List<Course> getSubscribedCourses(){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        LOGGER.info(ec.getRemoteUser());
        List<Student> activeStudent = entityFacade.namedQueryOneParam("STUDENT.getByEmail", Student.class, "email", ec.getRemoteUser());
        LOGGER.info(activeStudent.toString());
        return null;
    }
}
