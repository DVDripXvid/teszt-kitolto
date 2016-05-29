package xyz.codingmentor.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;

/**
 *
 * @author Olivér
 */
@Entity
@NamedNativeQueries({
    @NamedNativeQuery(name = "OPTIONFILLEDALANSWER.findByStudentIdAndQuestionId", 
            query = "SELECT a FROM OptionalFilledAnswer a WHERE a.student.id = :studentId AND a.question.id = :questionId")
})
public class OptionalFilledAnswer extends FilledAnswer implements Serializable {

    @ManyToOne
    @JoinColumn(name = "OPTIONAL_ANSWER_ID")
    private OptionalAnswer answer;

    public OptionalAnswer getAnswer() {
        return answer;
    }

    public void setAnswer(OptionalAnswer answer) {
        this.answer = answer;
    }
    
}
