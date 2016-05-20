package xyz.codingmentor.ejb;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import xyz.codingmentor.ejb.facade.EntityFacade;
import xyz.codingmentor.entity.Course;
import xyz.codingmentor.entity.Teacher;
import xyz.codingmentor.entity.Test;

@ManagedBean
public class TeacherController {

    @EJB
    private EntityFacade ef;
    
    private Teacher loggedInTeacher;
    
    @PostConstruct
    public void init(){
        loggedInTeacher = ef.namedQueryOneParam("TEACHER.findByEmail", Teacher.class, "email",
                FacesContext.getCurrentInstance().getExternalContext().getRemoteUser()).get(0);
    }
    
    public List<Test> getTests(){
        return loggedInTeacher.getTests();
    }
    
    public List<Course> getCourses(){
        return loggedInTeacher.getCourses();
    }
}