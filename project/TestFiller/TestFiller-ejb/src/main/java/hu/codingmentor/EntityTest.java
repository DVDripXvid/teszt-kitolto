package hu.codingmentor;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *
 * @author Olivér
 */
@Entity
public class EntityTest implements Serializable {

    @Id@GeneratedValue
    private Long id;

    public EntityTest() {
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
}
