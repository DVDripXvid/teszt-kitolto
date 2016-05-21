package xyz.codingmentor.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
    @NamedQuery(name = "TEST.findAll",
            query = "SELECT t FROM Test t"),
    @NamedQuery(name = "TEST.searchByName",
            query = "SELECT t FROM Test t WHERE t.name LIKE CONCAT('%', :name, '%')")
})
public class Test implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Integer duration;
    @ManyToOne
    @JoinColumn(name = "COURSE_ID")
    private Course course;
    @OneToMany(mappedBy = "test")
    private List<Question> questions;
    @ManyToOne
    private Teacher teacher;
    @OneToMany(mappedBy = "test", fetch = FetchType.EAGER) 
    private List<FilledTest> filledTests;
    private Boolean active = false;

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

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public List<FilledTest> getFilledTests() {
        return filledTests;
    }

    public void setFilledTests(List<FilledTest> filledTests) {
        this.filledTests = filledTests;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
