package xyz.codingmentor.ejb.facade;

import java.util.List;
import javax.ejb.Stateless;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import xyz.codingmentor.entity.Course;

@Stateless(name = "courseFacade")
public class CourseFacade extends EntityFacade{

    
    
    public List<Course> getActiveCourses(){
        return entityManager.createNamedQuery("COURSES.findActiveCourses", Course.class).getResultList();
    }
    
//    public List<Course> getSubscribedCourses(){
//        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
//        
//    }
}
