package xyz.codingmentor.ejb.teacher;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import xyz.codingmentor.ejb.facade.EntityFacade;
import xyz.codingmentor.entity.FilledTest;
import xyz.codingmentor.entity.Test;

@ManagedBean
public class TestDetailsController {

    @EJB
    private EntityFacade ef;
    private HttpSession session;
    
    @PostConstruct
    private void init(){
        session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    }
    
    public List<FilledTest> getFilledTests(){
        return ((Test)session.getAttribute("testToDetails")).getFilledTests();
    }
    
    public String revision(FilledTest filledTest){
        session.setAttribute("revisionFilledTest", filledTest);
        return "revisionTest";
    }
}