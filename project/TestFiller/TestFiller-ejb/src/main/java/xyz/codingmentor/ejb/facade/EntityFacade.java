package xyz.codingmentor.ejb.facade;

import java.util.List;
import xyz.codingmentor.exception.RecordNotFoundException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import xyz.codingmentor.entity.Subject;

/**
 *
 * @author Olivér
 */
@Stateless(name = "entityFacade")
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

    public List<Subject> allSubject(){
        return entityManager.createNamedQuery("allSubject",Subject.class).getResultList();
    }
}