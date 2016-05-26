package xyz.codingmentor.entity;

import java.io.Serializable;
import javax.persistence.Entity;

/**
 *
 * @author Olivér
 */
@Entity
public class TextFilledAnswer extends FilledAnswer implements Serializable{

    private String text;
    
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
}
