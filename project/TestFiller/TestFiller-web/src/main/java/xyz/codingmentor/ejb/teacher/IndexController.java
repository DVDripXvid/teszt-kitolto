package xyz.codingmentor.ejb.teacher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpSession;
import xyz.codingmentor.ejb.facade.EntityFacade;
import xyz.codingmentor.entity.FilledTest;
import xyz.codingmentor.entity.OptionalAnswer;
import xyz.codingmentor.entity.OptionalFilledAnswer;
import xyz.codingmentor.entity.Question;
import xyz.codingmentor.entity.Student;
import xyz.codingmentor.entity.Teacher;
import xyz.codingmentor.entity.Test;
import xyz.codingmentor.entity.TextFilledAnswer;
import xyz.codingmentor.interceptor.LoggerInterceptor;

@ManagedBean
@Interceptors({LoggerInterceptor.class})
public class IndexController {

    @EJB
    private EntityFacade ef;
    private HttpSession session;

    @PostConstruct
    public void init() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        session = (HttpSession) ec.getSession(true);
        session.setMaxInactiveInterval(-1);
        session.setAttribute("teacher", ef.namedQueryOneParam("TEACHER.findByEmail", Teacher.class,
                "email", ec.getRemoteUser()).get(0));
    }
    
    public String getTeacherFullName(){
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        return teacher.getFirstName()+" "+teacher.getLastName()+"!";
    }
    
    public String goToCreateTest() {
        return "createTest";
    }

    public List<Test> getTests() {
        return ((Teacher) session.getAttribute("teacher")).getTests();
    }
    
    public void activate(Test test){
        test.setActive(!test.getActive());
        ef.update(test);
    }
    
    public int numberOfRevievable(Test test){
        int c = 0;
        for (FilledTest filledTest:  test.getFilledTests()){
            if (filledTest.getReady() == null){
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
        session.setAttribute("testToDetails", test);
        return "detailsTest";
    }

    public String goToManageQuestions(Test test) {
        session.setAttribute("test", test);
        session.setAttribute("questionsToRemoveById", new ArrayList<Long>());
        return "manageQuestion";
    }

    public String edit(Test test) {
        session.setAttribute("testToEdit", test);
        return "editTest";
    }
    
    public void delete(Test test) {
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        teacher.getTests().remove(test);
        ef.update(teacher);
        ef.delete(Test.class, test.getId());
    }
}