package xyz.codingmentor.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import xyz.codingmentor.annotation.Validate;

/**
 *
 * @author Olivér
 */
@Validate
@Entity
@NamedQueries({
    @NamedQuery(name = "TEST.findAll",
            query = "SELECT t FROM Test t"),
    @NamedQuery(name = "TEST.searchByName",
            query = "SELECT t FROM Test t WHERE t.name LIKE CONCAT('%', :name, '%')"),
    @NamedQuery(name = "TEST.findByCourseId", 
            query = "SELECT t FROM Test t WHERE t.course = :course")
})
public class Test implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private String name;
    @Min(20)
    private Integer duration;
    @ManyToOne
    @JoinColumn(name = "COURSE_ID")
    private Course course;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "TEST_ID")
    private List<Question> questions;
    @ManyToOne
    private Teacher teacher;
    @OneToMany 
    @JoinColumn(name = "TEST_ID")
    private List<FilledTest> filledTests = new ArrayList<>();
    private Boolean active = false;

    public Test(){
        this.questions = new ArrayList<>();
        this.filledTests = new ArrayList<>();
    }

    public Test(String name, Integer duration, Course course) {
        this.name = name;
        this.duration = duration;
        this.course = course;
        this.active = true;
        this.questions = new ArrayList<>();
        this.filledTests = new ArrayList<>();
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
