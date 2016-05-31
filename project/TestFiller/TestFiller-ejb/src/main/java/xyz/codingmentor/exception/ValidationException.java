package xyz.codingmentor.exception;

import javax.ejb.ApplicationException;

/**
 *
 * @author Ákos
 */

@ApplicationException
public class ValidationException extends RuntimeException{
    
    public ValidationException(String msg) {
        super(msg);
    }

    public ValidationException() {
        super();
    }

}
