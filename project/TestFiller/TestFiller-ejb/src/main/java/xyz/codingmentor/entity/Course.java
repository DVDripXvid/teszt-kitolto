package xyz.codingmentor.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Olivér
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "COURSE.findAll",
            query = "Select c from Course c"),
    @NamedQuery(name = "COURSE.searchByNameAndTime",
            query = "SELECT c FROM Course c WHERE c.name LIKE CONCAT('%',:name,'%') AND c.time =:time"),
    @NamedQuery(name = "COURSE.findByName", 
            query = "SELECT c from Course c Where c.name LIKE :name"),
    @NamedQuery(name = "COURSE.findForUser",
            query = "SELECT c FROM Course c WHERE :student NOT MEMBER OF c.students AND :student NOT MEMBER OF c.subscribers")
})
public class Course implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private String name;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "COURSE_TIME")
    private Date time;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Subject> subjects;
    @ManyToMany(mappedBy = "courses")
    private List<Student> students;
    @ManyToMany(mappedBy = "courses")
    private List<Teacher> teachers;
    @OneToMany(mappedBy = "course")
    private List<FilledTest> filledTests;
    @OneToMany(mappedBy = "course", fetch = FetchType.EAGER)
    private List<Test> tests;
    @OneToMany(mappedBy = "subscribed", fetch = FetchType.EAGER)
    private List<Student> subscribers;

    public List<Student> getStudents() {
        return students;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    public List<FilledTest> getFilledTests() {
        return filledTests;
    }

    public void setFilledTests(List<FilledTest> filledTests) {
        this.filledTests = filledTests;
    }

    public List<Test> getTests() {
        return tests;
    }

    public void setTests(List<Test> tests) {
        this.tests = tests;
    }

    public List<Student> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(List<Student> Subscribers) {
        this.subscribers = Subscribers;
    }

}
