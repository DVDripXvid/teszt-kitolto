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
                    + "AND ft.ready = FALSE"),
    @NamedQuery(name = "FILLEDTEST.findByStudentIdAndTestIdAndReady",
            query = "SELECT ft "
                    + "FROM FilledTest ft "
                    + "WHERE ft.student.id = :studentId "
                    + "AND ft.test.id = :testId "
                    + "AND ft.ready = TRUE")
})
public class FilledTest implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private Boolean ready;
    @ManyToOne
    @JoinColumn(name = "COURSE_ID")
    private Course course;
    @ManyToOne
    private Test test;
    @ManyToOne
    @JoinColumn(name = "STUDENT_ID")
    private Student student;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "FILLED_TEST_ID")
    private List<FilledAnswer> filledAnswers = new ArrayList<>();
    private Float finalResult = 0F;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isReady() {
        return ready;
    }

    public void setReady(Boolean ready) {
        this.ready = ready;
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

    public Float getFinalResult() {
        if (filledAnswers != null && filledAnswers.size() > 0) {
            for (FilledAnswer filledAnswer : filledAnswers) {
                finalResult += filledAnswer.getPoint();
            }
            
            return finalResult / filledAnswers.size();
        }else {
            return null;
        }
    }

    public void setFinalResult(Float finalResult) {
        this.finalResult = finalResult;
    }
}
