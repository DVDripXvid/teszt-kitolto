package xyz.codingmentor.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
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
                query = "SELECT s FROM User s WHERE s.email=:email")
})
public class Student extends User implements Serializable {

    @OneToMany(mappedBy = "student")
    private List<FilledTest> filledTests; 
    @ManyToMany
    private List<Course> courses;

    public Student() {
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
    
    

}
