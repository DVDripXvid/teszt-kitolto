package xyz.codingmentor.ejb.teacher;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import xyz.codingmentor.ejb.facade.EntityFacade;
import xyz.codingmentor.entity.Course;
import xyz.codingmentor.entity.FilledTest;
import xyz.codingmentor.entity.OptionalAnswer;
import xyz.codingmentor.entity.OptionalFilledAnswer;
import xyz.codingmentor.entity.Question;
import xyz.codingmentor.entity.Student;
import xyz.codingmentor.entity.Teacher;
import xyz.codingmentor.entity.Test;
import xyz.codingmentor.entity.TextFilledAnswer;

@ManagedBean
@SessionScoped
public class TeacherController implements Serializable{

    @EJB
    private EntityFacade ef;
    private Teacher teacher;
    private Test test;

    @PostConstruct
    public void init() {
        teacher = ef.namedQueryOneParam("TEACHER.findByEmail", Teacher.class,
                "email", FacesContext.getCurrentInstance().getExternalContext().getRemoteUser()).get(0);
    }

    public Test goToCreateTest() {
        test = new Test();
        return test;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }
    
    public List<Course> getCourses() {
        return teacher.getCourses();
    }

    public void createTest() {
        test.setTeacher(teacher);
        teacher.getTests().add(test);
        ef.create(test);
        ef.update(teacher);
    }

    public List<Test> getTests() {
        return teacher.getTests();
    }

    public void activate(Test test) {
        test.setActive(!test.getActive());
        ef.update(test);
    }

    public int numberOfRevievable(Test test) {
        int c = 0;
        for (FilledTest filledTest : test.getFilledTests()) {
            if (filledTest.isReady() == true) {
                c++;
            }
        }
        return c;
    }

    public void editTest() {
        ef.update(test);
    }

    public void deleteTest(Test test) {
        teacher.getTests().remove(test);
        ef.update(teacher);
        ef.delete(Test.class, test.getId());
        init();
    }
}