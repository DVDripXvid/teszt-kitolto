package hu.codingmentor.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Olivér
 */
@Entity
public class Course implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "COURSE_TIME")
    private Date time;
    @ManyToMany
    private List<Subject> subjects;
    @ManyToMany
    private List<Student> students;
    @ManyToMany
    private List<Teacher> teachers;
    @OneToMany(mappedBy = "course")
    private List<FilledTest> filledTests;

}
