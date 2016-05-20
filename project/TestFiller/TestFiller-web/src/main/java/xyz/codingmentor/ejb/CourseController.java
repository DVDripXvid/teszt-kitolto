package xyz.codingmentor.ejb;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import xyz.codingmentor.ejb.facade.CourseFacade;
import xyz.codingmentor.entity.Course;

@Named
@SessionScoped
public class CourseController {

    @EJB
    private CourseFacade courseFacade;
    
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
    
    
}
