package xyz.codingmentor.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author Olivér
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "TEXTFILLEDANSWER.findByUserId", 
            query = "SELECT a FROM TextFilledAnswer a WHERE a.student.id = :studentId AND a.question.id = :questionId")
})
public class TextFilledAnswer extends FilledAnswer implements Serializable{

    private String text;
    
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String getAnswerForQuestion() {
        return text;
    }
}
