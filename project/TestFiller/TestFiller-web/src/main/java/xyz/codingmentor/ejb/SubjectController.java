package xyz.codingmentor.ejb;

import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import xyz.codingmentor.ejb.facade.EntityFacade;
import xyz.codingmentor.entity.Subject;

@ManagedBean
public class SubjectController {

    @EJB(name = "entityFacade")
    private EntityFacade ef;
    
    private Subject subject = new Subject();

    public void create(){
        ef.create(subject);
    }
    
    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
    
    public List<Subject> getSubjects(){
        return ef.namedQuery("allSubject", Subject.class);
    }
}