package hu.codingmentor.ejb;

import hu.codingmentor.EntityTest;
import hu.codingmentor.ejb.facade.EntityFacade;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Olivér
 */
@Startup
@Singleton
public class PersistenceTester {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersistenceTester.class);
    
    @EJB
    private EntityFacade facade;
    
    @PostConstruct
    public void createEntity(){
        LOGGER.info("singleton created: " + this);
        facade.create(new EntityTest());
    }
}
