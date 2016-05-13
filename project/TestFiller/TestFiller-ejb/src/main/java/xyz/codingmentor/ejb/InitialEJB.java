package xyz.codingmentor.ejb;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.codingmentor.ejb.facade.RoleFacade;
import xyz.codingmentor.entity.Role;
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
        //emailService.sendEmail("adamkassai@gmail.com", "maybe working", "trojan virus, sry");
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
        facade.create(user);
        facade.findRole("ADMIN").getUsers().add(user);
        user = new User("Teacher", "Bela", "pass" ,"teacher");
        facade.create(user);
        facade.findRole("TEACHER").getUsers().add(user);
        user = new User("Student", "Laci", "pass" ,"student");
        facade.create(user);
        facade.findRole("STUDENT").getUsers().add(user);
    }
    
    
}
