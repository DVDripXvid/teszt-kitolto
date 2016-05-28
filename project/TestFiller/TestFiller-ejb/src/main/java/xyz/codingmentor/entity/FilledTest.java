package xyz.codingmentor.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author Olivér
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "FILLEDTEST.findByStudentIdAndTestId",
            query = "SELECT ft "
                    + "FROM FilledTest ft "
                    + "WHERE ft.student.id = :studentId "
                    + "AND ft.test.id = :testId "
                    + "AND ft.ready = FALSE")
})
public class FilledTest implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private Boolean ready;
    @Column(name = "TEST_RESULT")
    private Float result;
    @ManyToOne
    @JoinColumn(name = "COURSE_ID")
    private Course course;
    @ManyToOne
    private Test test;
    @ManyToOne
    @JoinColumn(name = "STUDENT_ID")
    private Student student;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "FILLED_ANSWER_ID")
    private List<FilledAnswer> filledAnswers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getReady() {
        return ready;
    }

    public void setReady(Boolean ready) {
        this.ready = ready;
    }

    public Float getResult() {
        return result;
    }

    public void setResult(Float result) {
        this.result = result;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public List<FilledAnswer> getFilledAnswers() {
        return filledAnswers;
    }

    public void setFilledAnswer(List<FilledAnswer> filledAnswers) {
        this.filledAnswers = filledAnswers;
    }
}
