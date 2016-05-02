package hu.codingmentor.ejb.facade;

import hu.codingmentor.exception.RecordNotFoundException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Olivér
 */
@Stateless
public class EntityFacade {

    @PersistenceContext(unitName = "testsPU")
    protected EntityManager entityManager;
    
    public <T> void create(T entity){
        entityManager.persist(entity);
    }
    
    public <T> T read(Class<T> clazz, Long id){
        T entity = entityManager.find(clazz, id);
        if(entity == null){
            throw new RecordNotFoundException("no" + clazz.getSimpleName() + " exist with specified id: " + id);
        }
        return entityManager.find(clazz, id);
    }
    
    public <T> T update(T entity){
        return entityManager.merge(entity);
    }
    
    public <T> T delete(Class<T> clazz, Long id){
        T entity = read(clazz, id);
        entityManager.remove(entity);
        return entity;
    }
       
}