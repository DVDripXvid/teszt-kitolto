package xyz.codingmentor.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import xyz.codingmentor.annotation.Validate;

/**
 *
 * @author Olivér
 */
@Entity
@Validate
public class Question implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    @NotNull @Size(min = 5)
    private String text;
    @Enumerated
    private QuestionType type;
    @ManyToOne
    private Test test;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<FilledAnswer> filledAnswers;
    private Integer lengthOfAnswer;
    
    public Question() {
        this.filledAnswers = new ArrayList<>();
    }

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

    public List<FilledAnswer> getFilledAnswers() {
        return filledAnswers;
    }

    public void setFilledAnswers(List<FilledAnswer> filledAnswers) {
        this.filledAnswers = filledAnswers;
    }

}
