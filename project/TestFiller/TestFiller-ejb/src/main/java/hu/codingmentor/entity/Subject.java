package hu.codingmentor.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

/**
 *
 * @author Olivér
 */
@Entity
public class Subject implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @ManyToMany
    private List<Course> courses;
    @OneToMany(mappedBy = "subject")
    private List<Test> tests;

}
