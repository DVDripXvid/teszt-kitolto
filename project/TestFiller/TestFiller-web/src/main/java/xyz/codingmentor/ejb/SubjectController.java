package xyz.codingmentor.ejb;

import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import xyz.codingmentor.ejb.facade.EntityFacade;
import xyz.codingmentor.entity.Subject;

@Named
public class SubjectController {

    @EJB(name = "entityFacade")
    private EntityFacade ef;
    
    private Subject subject;

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