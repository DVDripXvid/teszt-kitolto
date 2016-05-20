package xyz.codingmentor.exception;

/**
 *
 * @author Olivér
 */
public class RecordNotFoundException extends RuntimeException{

    public RecordNotFoundException(String message){
        super(message);
    }
    
}