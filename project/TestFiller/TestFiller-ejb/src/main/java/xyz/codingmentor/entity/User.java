package xyz.codingmentor.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import xyz.codingmentor.annotation.Validate;

/**
 *
 * @author Olivér
 */
@Validate
@Entity
@Table(name = "USERS")
@NamedQueries({
    @NamedQuery(name = "USERS.findNonAccepted",
            query = "SELECT u FROM User u WHERE u.accepted=FALSE"),
    @NamedQuery(name = "USERS.findByEmail",
            query = "SELECT u FROM User u WHERE u.email=:email"),
    @NamedQuery(name = "USERS.findAll",
            query = "SELECT u FROM User u")
})
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    @NotNull @Size(min = 2)
    private String firstName;
    @NotNull @Size(min = 2)
    private String lastName;
    private String password;
    @Column(unique = true)
    @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
    private String email;
    private boolean accepted;
    @Temporal(TemporalType.DATE)
    private Date registrationDate;
    @Lob
    private byte[] image;
    @Column(name="IS_ADMIN")
    private boolean admin;
    
    @ManyToMany(cascade= CascadeType.REMOVE)
    @JoinTable(inverseJoinColumns = {
        @JoinColumn(name = "ROLE_NAME")
    }, joinColumns = {
        @JoinColumn(name = "USER_ID")
    })
    private List<Role> roles = new ArrayList<>();
    
    public User() {
    }

    public User(String firstName, String lastName, String password, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.accepted = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
    
    public boolean changeAdmin(){
        return false;
    }    
}
