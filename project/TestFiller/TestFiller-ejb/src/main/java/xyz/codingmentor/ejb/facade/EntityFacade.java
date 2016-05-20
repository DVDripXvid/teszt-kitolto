package xyz.codingmentor.ejb.facade;

import java.util.List;
import xyz.codingmentor.exception.RecordNotFoundException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
    
    public List namedQuery(String name, Class clazz){
        return entityManager.createNamedQuery(name, clazz).getResultList();
    }
    
    public <T> List namedQueryOneParam(String name, Class clazz, String paramName, T paramValue){
        return entityManager.createNamedQuery(name, clazz)
                .setParameter(paramName, paramValue).getResultList();
    }
    
    public <T> List namedQueryTwoParam(String name, Class clazz, String firstParamName
            , T firstParamValue, String secondParamName, T secondParamValue){
        return entityManager.createNamedQuery(name, clazz)
                .setParameter(firstParamName, firstParamValue)
                .setParameter(secondParamName, secondParamValue).getResultList();
    }
}