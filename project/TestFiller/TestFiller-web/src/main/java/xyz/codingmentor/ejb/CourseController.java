package xyz.codingmentor.ejb;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import xyz.codingmentor.ejb.facade.CourseFacade;
import xyz.codingmentor.entity.Course;

@ManagedBean
@ApplicationScoped
public class CourseController {

    @EJB
    private CourseFacade courseFacade;
    private static final List<String> accordingToSubscribeTypes = new ArrayList<>();
    private String selectedSubscribedType;
    private Course selectedCourse;
    
    @PostConstruct
    public void init(){
        Course c = new Course();
        c.setName("JavaEE");
        courseFacade.create(c);
        Course c2 = new Course();
        c2.setName("C++");
        courseFacade.create(c2);
    }
    
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
    
    public List<Course> getCourses(){
        return courseFacade.namedQuery("COURSE.findAll", Course.class);
    }

    public Course getSelectedCourse() {
        return selectedCourse;
    }

    public void setSelectedCourse(Course selectedCourse) {
        this.selectedCourse = selectedCourse;
    }
    
    public void deleteCourse(long id){
        Course c = courseFacade.read(Course.class,id);
        courseFacade.delete(Course.class, c.getId());
    }
}
