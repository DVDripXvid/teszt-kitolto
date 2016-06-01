package xyz.codingmentor.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import xyz.codingmentor.annotation.Validate;

/**
 *
 * @author Olivér
 */
@Validate
@Entity
@NamedQuery(name = "allSubject",query = "Select s From Subject s")
public class Subject implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    @NotNull @Size(min = 3)
    private String name;

    public Subject() {
        super();
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
}
