package xyz.codingmentor.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 *
 * @author Olivér
 */
@Entity
public class Teacher extends User implements Serializable {

    @OneToMany(cascade = CascadeType.ALL)
    private List<Course> courses;
    @OneToMany(mappedBy = "teacher")
    private List<Test> tests;

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

}