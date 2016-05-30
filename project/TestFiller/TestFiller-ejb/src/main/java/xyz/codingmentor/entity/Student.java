package xyz.codingmentor.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author Olivér
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "STUDENT.getByEmail",
            query = "SELECT s FROM Student s WHERE s.email=:email"),
    @NamedQuery(name = "STUDENT.getSubscribers",
            query = "SELECT s FROM Student s WHERE s.subscribed IS NOT NULL")
})
public class Student extends User implements Serializable {

    @OneToMany(mappedBy = "student")
    private List<FilledTest> filledTests;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Course> courses = new ArrayList<>();
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Course subscribed;

    public Student() {
    }
    
    public Student(User user){
        super(user.getFirstName(), user.getLastName(), user.getPassword(), user.getEmail());
    }

    public Student(String firstName, String lastName, String password, String email) {
        super(firstName, lastName, password, email);
    }

    public List<FilledTest> getFilledTests() {
        return filledTests;
    }

    public void setFilledTests(List<FilledTest> filledTests) {
        this.filledTests = filledTests;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public Course getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(Course subscribed) {
        this.subscribed = subscribed;
    }
    
}