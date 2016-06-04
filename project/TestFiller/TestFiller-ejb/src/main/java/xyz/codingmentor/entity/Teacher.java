package xyz.codingmentor.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author Olivér
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "TEACHER.findByEmail",
            query = "SELECT t FROM Teacher t WHERE t.email=:email")
})
public class Teacher extends User implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "Teacher_Id")
    private List<Course> courses = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "TEACHER_ID")
    private List<Test> tests = new ArrayList<>();

    public Teacher() {
    }

    public Teacher(String firstName, String lastName, String password, String email) {
        super(firstName, lastName, password, email);
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public List<Test> getTests() {
        return tests;
    }

    public void setTests(List<Test> tests) {
        this.tests = tests;
    }

    @Override
    public boolean changeAdmin() {
        setAdmin(!isAdmin());
        return true;
    }

}
