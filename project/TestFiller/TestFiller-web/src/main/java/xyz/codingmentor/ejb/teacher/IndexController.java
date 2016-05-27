package xyz.codingmentor.ejb.teacher;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import xyz.codingmentor.ejb.facade.EntityFacade;
import xyz.codingmentor.entity.FilledTest;
import xyz.codingmentor.entity.Question;
import xyz.codingmentor.entity.Student;
import xyz.codingmentor.entity.Teacher;
import xyz.codingmentor.entity.Test;
import xyz.codingmentor.entity.TextFilledAnswer;

@ManagedBean
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
            if (filledTest.getReady() && filledTest.getResult() == null){
                c++;
            }
        }
        return c;
    }
    
    public String details(Test test) {
        FilledTest ft = new FilledTest();
        ft.setResult(20F);
        Student s = new Student();
        s.setFirstName("Lajos");
        s.setLastName("Feri");
        ft.setStudent(s);
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
