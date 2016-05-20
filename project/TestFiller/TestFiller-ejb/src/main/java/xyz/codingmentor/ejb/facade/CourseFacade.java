package xyz.codingmentor.ejb.facade;

import java.util.List;
import javax.ejb.Stateless;
import xyz.codingmentor.entity.Course;

@Stateless(name = "courseFacade")
public class CourseFacade extends EntityFacade{

    public List<Course> getActiveCourses(){
        return entityManager.createNamedQuery("COURSES.findActiveCourses", Course.class).getResultList();
    }
    
    public void getSubscribtions(){
        //entityManager.createNativeQuery("SELECT ")
    }
}
