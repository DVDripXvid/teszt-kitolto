package hu.codingmentor.entity;

import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Olivér
 */
@MappedSuperclass
public class User {

    @Id
    @GeneratedValue
    private Long id;
    private String fistName;
    private String lastName;
    private String password;
    private String email;
    @Temporal(TemporalType.DATE)
    private Date registrationDate;
    @Lob
    private byte[] image;
    
}
