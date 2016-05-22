package xyz.codingmentor.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

/**
 *
 * @author Olivér
 */
@Entity
@NamedQuery(name = "questionTestNull", query = "SELECT q FROM Question q WHERE q.test = :null")
public class Question implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private String text;
    @Enumerated
    private QuestionType type;
    @ManyToOne
    private Test test;
    private Integer lengthOfAnswer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public QuestionType getType() {
        return type;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public Integer getLengthOfAnswer() {
        return lengthOfAnswer;
    }

    public void setLengthOfAnswer(Integer lengthOfAnswer) {
        this.lengthOfAnswer = lengthOfAnswer;
    }
}
