package xyz.codingmentor.ejb.teacher;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
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
@ViewScoped
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

    public List<Course> getCourses() {
        return teacher.getCourses();
    }

    public void createTest() {
        test.setTeacher(teacher);
        teacher.getTests().add(test);
        ef.create(test);
        ef.update(teacher);
        test = null;
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

    public String details(Test test) {
        FilledTest ft = new FilledTest();
        Student s = new Student();
        s.setFirstName("Lajos");
        s.setLastName("Feri");
        ft.setStudent(s);
        TextFilledAnswer tfa = new TextFilledAnswer();
        tfa.setText("asd");
        OptionalFilledAnswer ofa = new OptionalFilledAnswer();
        OptionalAnswer oa = new OptionalAnswer();
        oa.setText("text");
        ofa.setAnswer(oa);
        ofa.setComment("comment");
        ofa.setQuestion(new Question());
        tfa.setQuestion(new Question());
        ft.setFilledAnswer(Arrays.asList(tfa, ofa));
        ft.setReady(Boolean.TRUE);
        test.getFilledTests().add(ft);
        return "detailsTest";
    }

    public void goToManageQuestions(Test test) {

    }

    public void editTest() {
        ef.update(test);
        test = null;
    }

    public void deleteTest(Test test) {
        teacher.getTests().remove(test);
        ef.delete(Test.class, test.getId());
        ef.update(teacher);
        init();
    }
}
