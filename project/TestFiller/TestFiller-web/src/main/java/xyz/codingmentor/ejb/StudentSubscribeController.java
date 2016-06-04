package xyz.codingmentor.ejb;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.interceptor.Interceptors;
import org.primefaces.model.LazyDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.codingmentor.ejb.facade.EntityFacade;
import xyz.codingmentor.entity.Course;
import xyz.codingmentor.entity.Student;
import xyz.codingmentor.interceptor.LoggerInterceptor;
import xyz.codingmentor.model.LazyCourseDataModel;

@ManagedBean
@SessionScoped
@Interceptors({LoggerInterceptor.class})
public class StudentSubscribeController implements Serializable {

    private static final String SuccessfullSubscribeMessage = "Subscription OK!";
    private static final String SuccessfullUnsubscribeMessage = "Unsubscription OK!";
    private static final String AlreadySubscribedMessage = "Your last subscribe demand is not accepted yet.";

    private final ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentSubscribeController.class);

    @EJB
    private EntityFacade entityFacade;
    private Student activeStudent;
    private List<Course> subscribedCourses;
    private LazyDataModel<Course> allCourses;
    private List<Course> filteredCourseList;

    public StudentSubscribeController() {
    }

    public void manageCourses() {
        activeStudent = entityFacade.namedQueryOneParam("STUDENT.getByEmail", Student.class, "email", ec.getRemoteUser()).get(0);
        allCourses = new LazyCourseDataModel(entityFacade.namedQueryOneParam("COURSE.findForUser", Course.class, "student", activeStudent));

        try {
            ec.redirect(ec.getApplicationContextPath() + "/faces/student/manageCourses.xhtml");
        } catch (IOException ex) {
            LOGGER.info(ex.getMessage());
        }
    }

    public List<Course> getSubscribedCourses() {
        return subscribedCourses;
    }

    public void setSubscribedCourses(List<Course> subscribedCourses) {
        this.subscribedCourses = subscribedCourses;
    }

    public List<Course> getFilteredCourseList() {
        return filteredCourseList;
    }

    public void setFilteredCourseList(List<Course> filteredCourseList) {
        this.filteredCourseList = filteredCourseList;
    }

    public LazyDataModel<Course> getAllCourses() {
        allCourses = new LazyCourseDataModel(entityFacade.namedQueryOneParam("COURSE.findForUser", Course.class, "student", activeStudent));
        return allCourses;
    }

    public void setAllCourses(LazyDataModel<Course> allCourses) {
        this.allCourses = allCourses;
    }

    public List<Course> getCoursesByUser() {
        return activeStudent.getCourses();
    }

    public void subscribeToCourse(Course selectedCourse) {
        if (activeStudent.getSubscribed() == null) {
            activeStudent.setSubscribed(selectedCourse);
            selectedCourse.getSubscribers().add(activeStudent);
            entityFacade.update(activeStudent);
            entityFacade.update(selectedCourse);

            //successfullSubscriptionMessage();
        } else {
            //warnMessage();
        }
    }

    public void unsubscribeFromCourse(Course course) {
        activeStudent.setSubscribed(null);
        course.getSubscribers().remove(activeStudent);
        entityFacade.update(activeStudent);
        entityFacade.update(course);

        //successfullUnsubscriptionMessage();
    }

    public List<Course> getStudentSubscibedCourse() {
        return activeStudent.getSubscribed() != null 
                ? new ArrayList<>(Arrays.asList(activeStudent.getSubscribed())) : new ArrayList<Course>();
    }

    public boolean filterByPrice(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim();
        if (filterText == null || filterText.equals("")) {
            return true;
        }

        if (value == null) {
            return false;
        }

        return ((Comparable) value).compareTo(Date.valueOf(filterText)) >= 0;
    }

    private void successfullSubscriptionMessage() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, SuccessfullSubscribeMessage, null));

    }

    private void successfullUnsubscriptionMessage() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, SuccessfullUnsubscribeMessage, null));
    }

    private void warnMessage() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, AlreadySubscribedMessage, null));
    }
}
