package xyz.codingmentor.ejb;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import xyz.codingmentor.ejb.facade.EntityFacade;
import xyz.codingmentor.entity.Test;

@ManagedBean
public class TestController {

    @EJB(name = "entityFacade")
    private EntityFacade ef;

    private Test test = new Test();
    
    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }
    
    
}
