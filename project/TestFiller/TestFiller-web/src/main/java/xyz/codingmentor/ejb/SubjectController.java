package xyz.codingmentor.ejb;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.interceptor.Interceptors;
import org.primefaces.event.CellEditEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.codingmentor.ejb.facade.EntityFacade;
import xyz.codingmentor.entity.Subject;
import xyz.codingmentor.interceptor.LoggerInterceptor;

@ManagedBean
@ViewScoped
@Interceptors({LoggerInterceptor.class})
public class SubjectController implements Serializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(SubjectController.class);
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
    
    public void removeSubject(Subject subject){
        ef.delete(Subject.class, subject.getId());
    }
    public void onCellEdit(CellEditEvent event){
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        
        LOGGER.info("uj: " + newValue.toString() + " regi: " + oldValue);
        if(!newValue.equals(oldValue)){
            oldValue = newValue;
        }
    }
    
    public void save(){
        ef.update(subject);
    }
}