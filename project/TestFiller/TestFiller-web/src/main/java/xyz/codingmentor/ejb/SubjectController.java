package xyz.codingmentor.ejb;

import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import xyz.codingmentor.entity.Subject;
import xyz.codingmentor.service.SubjectService;

@ManagedBean
public class SubjectController {

    @Inject
    private SubjectService subjectService;
    
    private Subject subject = new Subject();

    public void create(){
        subjectService.create(subject);
    }
    
    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}