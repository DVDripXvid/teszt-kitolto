package xyz.codingmentor.ejb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.interceptor.Interceptors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.codingmentor.ejb.facade.CourseFacade;
import xyz.codingmentor.entity.Course;
import xyz.codingmentor.interceptor.LoggerInterceptor;

@ManagedBean
@SessionScoped
@Interceptors({LoggerInterceptor.class})
public class CourseController implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(CourseController.class);
    private static final String UnSuccessfulCourseRemove = "Cannot remove course with subscribed student!";

    @EJB
    private CourseFacade courseFacade;
    private static final List<String> accordingToSubscribeTypes = new ArrayList<>();
    private String selectedSubscribedType;
    private Course selectedCourse = new Course();

    @PostConstruct
    public void init() {
        LOGGER.info("new one");
        selectedCourse.setName("try");
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

    public List<Course> getActiveCourses() {
        return courseFacade.getActiveCourses();
    }

    public List<Course> getCourses() {
        return courseFacade.namedQuery("COURSE.findAll", Course.class);
    }

    public Course getSelectedCourse() {
        return selectedCourse;
    }

    public void setSelectedCourse(Course selectedCourse) {
        this.selectedCourse = selectedCourse;
    }

    public void deleteCourse(long id) {
        Course c = courseFacade.read(Course.class, id);
        if (c.getSubscribers() != null) {
            UnSuccessfulCourseRemove();
        }
        courseFacade.delete(Course.class, c.getId());
    }

    public void save() {
        courseFacade.update(selectedCourse);
    }

    public void UnSuccessfulCourseRemove() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("WARNING!", UnSuccessfulCourseRemove));
    }
    
}
