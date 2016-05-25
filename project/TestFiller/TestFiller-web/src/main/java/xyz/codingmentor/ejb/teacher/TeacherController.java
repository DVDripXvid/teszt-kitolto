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
import xyz.codingmentor.entity.Teacher;
import xyz.codingmentor.entity.Test;

@ManagedBean
public class TeacherController {

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
    
    public String goToCreateTest(){
        return "createTest";
    }

    public String goToManageQuestions(Test test) {
        session.setAttribute("test", test);
        session.setAttribute("questionsToRemoveById", new ArrayList<Long>());
        return "manageQuestion";
    }

    public List<Test> getTests() {
        return ((Teacher) session.getAttribute("teacher")).getTests();
    }

    public void delete(Test test) {
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        teacher.getTests().remove(test);
        ef.update(teacher);
        ef.delete(Test.class, test.getId());
    }

    public String edit(Test test) {
        session.setAttribute("testToEdit", test);
        return "editTest";
    }

    public String details(Test test) {
        session.setAttribute("testToDetails", test);
        return "detailsTest";
    }
}