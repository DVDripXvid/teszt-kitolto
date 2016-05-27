package xyz.codingmentor.ejb.teacher;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import xyz.codingmentor.ejb.facade.EntityFacade;
import xyz.codingmentor.entity.Teacher;
import xyz.codingmentor.entity.Test;

@ManagedBean
public class CreateTestController {

    @EJB
    private EntityFacade ef;
    private HttpSession session;
    private Test test;

    @PostConstruct
    private void init() {
        test = new Test();
        session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    }

    public Test getTest() {
        return test;
    }

    public String create() {
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        test.setTeacher(teacher);
        teacher.getTests().add(test);
        ef.update(teacher);
        return "index";
    }

    public String back() {
        return "index";
    }
}
