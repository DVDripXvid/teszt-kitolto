package xyz.codingmentor.ejb;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.codingmentor.ejb.facade.EntityFacade;
import xyz.codingmentor.entity.Course;
import xyz.codingmentor.entity.Student;

@ManagedBean
@SessionScoped
public class StudentSubscribeController implements Serializable {

    private static final String SuccessfullSubscribeMessage = "Subscription OK!";
    private static final String SuccessfullUnsubscribeMessage = "Unsubscription OK!";

    private ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentSubscribeController.class);

    @EJB
    private EntityFacade entityFacade;

    private List<Course> subscribedCourses;

    public List<Course> getAllCourses() {
        Student activeStudent = entityFacade.namedQueryOneParam("STUDENT.getByEmail", Student.class, "email", ec.getRemoteUser()).get(0);
        List<Course> allCourses = entityFacade.namedQuery("COURSE.findAll", Course.class);
        return allCourses;
    }

    public List<Course> getCoursesByUser() {
        Student activeStudent = entityFacade.namedQueryOneParam("STUDENT.getByEmail", Student.class, "email", ec.getRemoteUser()).get(0);
        return activeStudent.getCourses();
    }

    public void subscribeToCourse(Course selectedCourse) {
        Student activeStudent = entityFacade.namedQueryOneParam("STUDENT.getByEmail", Student.class, "email", ec.getRemoteUser()).get(0);

        if (activeStudent.getSubscribed() == null) {
            activeStudent.setSubscribed(selectedCourse);
            selectedCourse.getSubscribers().add(activeStudent);
            entityFacade.update(activeStudent);

            successfullSubscriptionMessage();
        } else {
            warnMessage();
        }
    }
    
    public void unsubscribeFromCourse(Course course){
        Student activeStudent = entityFacade.namedQueryOneParam("STUDENT.getByEmail", Student.class, "email", ec.getRemoteUser()).get(0);
        
        activeStudent.setSubscribed(null);
        course.getSubscribers().remove(activeStudent);
        
        successfullUnsubscriptionMessage();
    }

    private void successfullSubscriptionMessage() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Successful", SuccessfullSubscribeMessage));
    }
    
    private void successfullUnsubscriptionMessage() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Successful", SuccessfullUnsubscribeMessage));
    }

    private void warnMessage() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning!", "Your last subscribe demand is not accepted yet."));
    }
    
    public Course getStudentSubscibedCourse(){
        Student activeStudent = entityFacade.namedQueryOneParam("STUDENT.getByEmail", Student.class, "email", ec.getRemoteUser()).get(0);
        return activeStudent.getSubscribed();
    }
}
