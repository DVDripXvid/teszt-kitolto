package xyz.codingmentor.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author Olivér
 */
@Entity
public class OptionalAnswer implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private Boolean correct;
    @ManyToOne
    @JoinColumn(name = "QUESTION_ID")
    private Question question;
    
}
