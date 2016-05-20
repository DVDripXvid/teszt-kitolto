package xyz.codingmentor.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
    @NamedQuery(name = "COURSE.listAll", 
            query = "Select c from Course c")
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
    private boolean isActive;
    @OneToMany(mappedBy = "course")
    private List<Test> tests;

    public List<Student> getStudents() {
        return students;
    }
}
