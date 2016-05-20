package xyz.codingmentor.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author Olivér
 */
@Entity
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
    @JoinColumn(name = "TEST_ID")
    private Test test;
    @ManyToOne
    @JoinColumn(name = "STUDENT_ID")
    private Student student;
    @OneToMany(mappedBy = "filledTest")
    private List<FilledAnswer> filledAnswers;
    
}
