package xyz.codingmentor.ejb;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import xyz.codingmentor.ejb.facade.CourseFacade;
import xyz.codingmentor.ejb.facade.UserFacade;
import xyz.codingmentor.entity.Course;
import xyz.codingmentor.entity.Student;
import xyz.codingmentor.entity.User;

@Named
@SessionScoped
public class CourseController {

    @EJB
    private CourseFacade courseFacade;
    
    @EJB UserFacade userFacade;
    
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
        Student activeStudent = (Student)userFacade.getByEmail(ec.getRemoteUser(), User.class);
        return activeStudent.getCourses();
    }
}
