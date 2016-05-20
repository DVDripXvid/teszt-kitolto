package xyz.codingmentor.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Olivér
 */
@Entity
@Table(name = "ROLES")
@NamedQueries({
    @NamedQuery(name = "Role.ByName",
            query = "SELECT r FROM Role r WHERE r.roleName=:name"),
    @NamedQuery(name = "Role.findAll",
            query = "SELECT r FROM Role r")
})
public class Role implements Serializable {

    @Id
    private String roleName;
    @ManyToMany(cascade = {CascadeType.ALL, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @JoinTable(inverseJoinColumns = {
        @JoinColumn(name = "USER_ID")
    }, joinColumns = {
        @JoinColumn(name = "ROLE_NAME")
    })
    private List<User> users = new ArrayList<>();

    public Role() {
    }

    public Role(String roleName) {
        this.roleName = roleName;
    }
    
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }    
    
}
