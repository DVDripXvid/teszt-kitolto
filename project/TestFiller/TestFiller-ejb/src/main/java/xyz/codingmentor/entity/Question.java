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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author Olivér
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "QUESTION.findByTestId", 
            query = "SELECT q FROM Question q WHERE q.test.id = :testId")
})
public class Question implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private String text;
    @Enumerated
    private QuestionType type;
    @ManyToOne
    private Test test;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<FilledAnswer> filledAnswers;
    private Integer lengthOfAnswer;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "Question_Id")
    private List<OptionalAnswer> optionalAnswers;
    
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

    public List<OptionalAnswer> getOptionalAnswers() {
        return optionalAnswers;
    }

    public void setOptionalAnswers(List<OptionalAnswer> optionalAnswers) {
        this.optionalAnswers = optionalAnswers;
    }
}
