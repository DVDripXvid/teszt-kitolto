package xyz.codingmentor.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author Olivér
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "OPTIONALANSWER.findByQuestionId",
            query = "SELECT o FROM OptionalAnswer o WHERE o.question.id = :questionId"),
    @NamedQuery(name = "OPTIONALANSWER.findByText",
            query = "SELECT o FROM OptionalAnswer o WHERE o.text LIKE :text")
})
public class OptionalAnswer implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private Boolean correct = Boolean.FALSE;
    @ManyToOne
    private Question question;
    private String text;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getCorrect() {
        return correct;
    }

    public void setCorrect(Boolean correct) {
        this.correct = correct;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
}
