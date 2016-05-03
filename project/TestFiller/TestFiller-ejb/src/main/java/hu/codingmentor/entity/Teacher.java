package hu.codingmentor.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

/**
 *
 * @author Olivér
 */
@Entity
public class Teacher extends User implements Serializable {

    @ManyToMany
    private List<Course> courses;
    @OneToMany(mappedBy = "teacher")
    private List<Test> tests;

}
