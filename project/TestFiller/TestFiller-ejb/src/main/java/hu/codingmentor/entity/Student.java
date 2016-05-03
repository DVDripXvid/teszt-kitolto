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
public class Student extends User implements Serializable {

    @OneToMany(mappedBy = "student")
    private List<FilledTest> filledTests; 
    @ManyToMany
    private List<Course> courses;

}
