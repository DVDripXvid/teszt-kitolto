package xyz.codingmentor.ejb;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.codingmentor.ejb.facade.EntityFacade;
import xyz.codingmentor.entity.Course;
import xyz.codingmentor.entity.Student;

@Named
@RequestScoped
public class StudentSubscribeController {

    private ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
    
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentSubscribeController.class);

    @EJB
    private EntityFacade entityFacade;

    private List<Course> subscribedCourses;
    
    private Course selectedCourse;

    public void setSelectedCourse(Course selectedCourse) {
        this.selectedCourse = selectedCourse;
    }

    public Course getSelectedCourse() {
        return selectedCourse;
    }

    public List<Course> getAllCourses() {
        return entityFacade.namedQuery("COURSE.findAll", Course.class);
    }

    public List<Course> getCoursesByUser() {        
        Student activeStudent = entityFacade.namedQueryOneParam("STUDENT.getByEmail", Student.class, "email", ec.getRemoteUser()).get(0);
        return activeStudent.getCourses();
    }
    
    public void subscribeToCourse(){
        Student activeStudent = entityFacade.namedQueryOneParam("STUDENT.getByEmail", Student.class, "email", ec.getRemoteUser()).get(0);
        activeStudent.setSubscribed(selectedCourse);
        entityFacade.update(activeStudent);
        
        activeStudent.getCourses().add(selectedCourse);
    }
}
