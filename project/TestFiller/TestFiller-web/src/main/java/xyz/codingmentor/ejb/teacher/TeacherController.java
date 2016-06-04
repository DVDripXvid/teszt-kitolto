package xyz.codingmentor.ejb.teacher;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import xyz.codingmentor.ejb.facade.EntityFacade;
import xyz.codingmentor.entity.Course;
import xyz.codingmentor.entity.FilledTest;
import xyz.codingmentor.entity.QueryName;
import xyz.codingmentor.entity.Teacher;
import xyz.codingmentor.entity.Test;

@ManagedBean
@SessionScoped
public class TeacherController implements Serializable {

    @EJB
    private EntityFacade ef;
    private Teacher teacher;
    private Test test;
    private String courseName;

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public List<Course> getCourses() {
        return teacher.getCourses();
    }

    public Test goToCreateTest() {
        test = new Test();
        courseName = null;
        return test;
    }

    public void createTest() {
        teacher.getTests().add(test);
        for (Course c : teacher.getCourses()) {
            if (c.getName().equals(courseName)) {
                test.setCourse(c);
            }
        }
        ef.update(teacher);
    }

    public List<Test> getTests() {
        teacher = ef.namedQueryOneParamSingleResult(QueryName.TEACHER_findByEmail,
                Teacher.class, "email", FacesContext.getCurrentInstance()
                .getExternalContext().getRemoteUser());
        return teacher.getTests();
    }

    public void activate(Test test) {
        test.setActive(!test.getActive());
        ef.update(test);
    }

    public int numberOfRevievable(Test test) {
        int c = 0;
        for (FilledTest filledTest : test.getFilledTests()) {
            if (filledTest.isReady() == true && filledTest.getFinalResult() != null) {
                c++;
            }
        }
        return c;
    }

    public Test goToEditTest(Test test) {
        courseName = test.getCourse().toString();
        return test;
    }

    public void editTest() {
        for (Course c : teacher.getCourses()) {
            if (c.getName().equals(courseName)) {
                test.setCourse(c);
            }
        }
        ef.update(test);
    }

    public void deleteTest(Test test) {
        teacher.getTests().remove(test);
        ef.update(teacher);
        Course course = test.getCourse();
        course.getTests().remove(test);
        ef.update(course);
        ef.delete(Test.class, test.getId());
    }
}
