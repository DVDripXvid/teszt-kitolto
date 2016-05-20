package xyz.codingmentor.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author Olivér
 */
@Entity
public class OptionalFilledAnswer extends FilledAnswer implements Serializable {

    @ManyToOne
    @JoinColumn(name = "OPTIONAL_ANSWER_ID")
    private OptionalAnswer answer;
    
}
