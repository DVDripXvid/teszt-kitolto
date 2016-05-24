package xyz.codingmentor.ejb;

import java.io.Serializable;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.Locale;
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
import xyz.codingmentor.entity.Test;

@ManagedBean
@SessionScoped
public class StudentSubscribeController implements Serializable {

    private static final String SuccessfullSubscribeMessage = "Subscription OK!";
    private static final String SuccessfullUnsubscribeMessage = "Unsubscription OK!";
    private static final String AlreadySubscribedMessage = "Your last subscribe demand is not accepted yet.";

    private final ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentSubscribeController.class);

    @EJB
    private EntityFacade entityFacade;
    private List<Course> subscribedCourses;
    private List<Course> allCourses;
    private List<Course> filteredCourseList;

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

    public List<Course> getAllCourses() {
        Student activeStudent = entityFacade.namedQueryOneParam("STUDENT.getByEmail", Student.class, "email", ec.getRemoteUser()).get(0);
        allCourses = entityFacade.namedQueryOneParam("COURSE.findForUser", Course.class, "student", activeStudent);
        return allCourses;
    }

    public void setAllCourses(List<Course> allCourses) {
        this.allCourses = allCourses;
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
            addTestsToCourse(selectedCourse);
            entityFacade.update(activeStudent);

            //successfullSubscriptionMessage();
        } else {
            //warnMessage();
        }
    }

    public void unsubscribeFromCourse(Course course) {
        Student activeStudent = entityFacade.namedQueryOneParam("STUDENT.getByEmail", Student.class, "email", ec.getRemoteUser()).get(0);
        activeStudent.setSubscribed(null);
        course.getSubscribers().remove(activeStudent);
        entityFacade.update(activeStudent);
        entityFacade.update(course);

        //successfullUnsubscriptionMessage();
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

    public List<Course> getStudentSubscibedCourse() {
        Student activeStudent = entityFacade.namedQueryOneParam("STUDENT.getByEmail", Student.class, "email", ec.getRemoteUser()).get(0);
        List<Course> returnList = new ArrayList<>();
        if (activeStudent.getSubscribed() == null) {
            returnList.clear();
            return returnList;
        } else {
            returnList.add(activeStudent.getSubscribed());
        }

        return returnList;
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

    private void addTestsToCourse(Course c) {
        c.getTests().add(new Test(c.getName() + " - Test1", 20, c));
        c.getTests().add(new Test(c.getName() + " - Test2", 20, c));
        c.getTests().add(new Test(c.getName() + " - Test3", 20, c));
        c.getTests().add(new Test(c.getName() + " - Test4", 20, c));
        c.getTests().add(new Test(c.getName() + " - Test5", 20, c));
        c.getTests().add(new Test(c.getName() + " - Test6", 20, c));
        c.getTests().add(new Test(c.getName() + " - Test7", 20, c));
    }
}
