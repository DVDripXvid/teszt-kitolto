package xyz.codingmentor.ejb;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import xyz.codingmentor.ejb.facade.RoleFacade;
import xyz.codingmentor.entity.Role;
import xyz.codingmentor.entity.Student;
import xyz.codingmentor.entity.Teacher;
import xyz.codingmentor.entity.User;
import xyz.codingmentor.role.RoleName;

/**
 *
 * @author Olivér
 */
@ManagedBean(name="registrationEJB")
@RequestScoped
public class RegistrationEJB implements Serializable{    
    
    @EJB(name = "emailService")
    private EmailService emailService;
    
    @EJB
    private RoleFacade facade;
    
    private User user = new User();
    
    private String selectedRole;
    
    private static final Map<String, String> ROLE_NAMES = new HashMap<>();
    
    static {
        ROLE_NAMES.put(RoleName.STUDENT, RoleName.STUDENT);
        ROLE_NAMES.put(RoleName.TEACHER, RoleName.TEACHER);
    }
    
    public String register(){
        Role role = facade.findRole(selectedRole);
        char[] pw = new char[6];
        for(int i=0; i<6; i++){
            pw[i] = (char) (new Random().nextInt(26)+65);
        }
        user.setPassword(String.copyValueOf(pw).toLowerCase());
        
        if (selectedRole.equals(RoleName.STUDENT)) {
            Student student = new Student(user.getFirstName(), user.getLastName(), user.getPassword(), user.getEmail());
            facade.create(student);
            role.getUsers().add(student);
        }
        else if (selectedRole.equals(RoleName.TEACHER)) {
            Teacher teacher = new Teacher(user.getFirstName(), user.getLastName(), user.getPassword(), user.getEmail());
            facade.create(teacher);
            role.getUsers().add(teacher);
        }
                
        facade.update(role);        
        return "notification";
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public Map<String, String> getRoleNames() {
        return ROLE_NAMES;
    }

    public String getSelectedRole() {
        return selectedRole;
    }

    public void setSelectedRole(String selectedRole) {
        this.selectedRole = selectedRole;
    }

}
