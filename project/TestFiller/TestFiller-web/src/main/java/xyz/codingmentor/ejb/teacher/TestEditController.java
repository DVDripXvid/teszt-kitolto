package xyz.codingmentor.ejb.teacher;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import xyz.codingmentor.ejb.facade.EntityFacade;
import xyz.codingmentor.entity.Test;

@ManagedBean
public class TestEditController {

    @EJB
    private EntityFacade ef;
    private HttpSession session;
    private Test test;
    
    @PostConstruct
    private void init(){
        session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        test = (Test) session.getAttribute("testToEdit");
    }

    public Test getTest() {
        return test;
    }
    
    public String edit(){
        ef.update(test);
        return "index";
    }
}