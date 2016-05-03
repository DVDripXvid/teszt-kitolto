package hu.codingmentor.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author Olivér
 */
@Entity
public class Question implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private String text;
    @Enumerated
    private QuestionType type;
    @ManyToOne
    @JoinColumn(name = "TEST_ID")
    private Test test;
    
}
