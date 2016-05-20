package xyz.codingmentor.ejb;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.codingmentor.ejb.facade.RoleFacade;
import xyz.codingmentor.entity.Course;
import xyz.codingmentor.entity.Role;
import xyz.codingmentor.entity.Student;
import xyz.codingmentor.entity.Teacher;
import xyz.codingmentor.entity.Test;
import xyz.codingmentor.entity.User;
import xyz.codingmentor.role.RoleName;

/**
 *
 * @author Olivér
 */
@Startup
@Singleton
public class InitialEJB {

    private static final Logger LOGGER = LoggerFactory.getLogger(InitialEJB.class);
    
    @EJB
    private RoleFacade facade;
    
    @EJB
    private EmailService emailService;
    
    @PostConstruct
    public void createEntity(){
        LOGGER.info("singleton created: " + this);
        createRoles();
        createUser();
        generateTestData();
        //emailService.sendEmail("adamkassai@gmail.com", "maybe working", "trojan virus, sry");
    }
    
    private void generateTestData(){
        Course course = new Course();
        course.setName("course");
        facade.create(course);
        Student student = new Student("Student", "wantCourse", "pass", "wantcourse");
        student.setSubscribed(course);
        facade.create(student);
        
        Test test = new Test();
        test.setName("test");
        facade.create(test);
        Teacher teacher = new Teacher("Teacher", "WithTest", "pass", "withtest");
        teacher.getTests().add(test);
        facade.create(test);
    }
       
    private void createRoles(){
        Role role = new Role(RoleName.ADMIN);
        facade.create(role);
        role = new Role(RoleName.TEACHER);
        facade.create(role);
        role = new Role(RoleName.STUDENT);
        facade.create(role);
    }
    
    private void createUser(){
        User user = new User("Admin", "Lajos", "pass" ,"admin");
        user.setAccepted(true);
        facade.create(user);
        facade.findRole("ADMIN").getUsers().add(user);
        Teacher teacher = new Teacher("Teacher", "Bela", "pass" ,"teacher");
        teacher.setAccepted(true);
        facade.create(teacher);
        facade.findRole("TEACHER").getUsers().add(teacher);
        Student student = new Student("Student", "Laci", "pass" ,"student");
        student.setAccepted(true);
        facade.create(student);
        facade.findRole("STUDENT").getUsers().add(student);
    }
    
    
}
