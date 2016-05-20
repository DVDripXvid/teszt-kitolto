package xyz.codingmentor.ejb.facade;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import xyz.codingmentor.entity.Course;

@Stateless(name = "courseFacade")
public class CourseFacade extends EntityFacade{
    
    public List<Course> getActiveCourses(){
        return entityManager.createNamedQuery("COURSE.listAll", Course.class).getResultList();
    }
    
    public void getSubscribtions(){
        //entityManager.createNativeQuery("SELECT ")
    }
}
