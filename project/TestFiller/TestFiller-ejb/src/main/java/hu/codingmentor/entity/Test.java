package hu.codingmentor.entity;

import java.io.Serializable;
import java.util.List;
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
public class Test implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Integer duration;
    @ManyToOne
    @OneToMany(mappedBy = "test")
    private List<Question> questions;
    @ManyToOne
    @JoinColumn(name = "TEACHER_ID")
    private Teacher teacher;
    @OneToMany(mappedBy = "test")
    private List<FilledTest> filledTests;
    @ManyToOne
    @JoinColumn(name = "COURSE_ID")
    private Course course;
    
}
