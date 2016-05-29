package xyz.codingmentor.ejb;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import xyz.codingmentor.ejb.facade.EntityFacade;
import xyz.codingmentor.entity.FilledTest;
import xyz.codingmentor.entity.QueryName;
import xyz.codingmentor.entity.Test;


@Named
@SessionScoped
public class TestController implements Serializable{
    
    @EJB(name = "entityFacade")
    private EntityFacade facade;
    
    private Test test;

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }
    
    public List<Test> getTests(){
        return facade.namedQuery(QueryName.TEST_findAll, Test.class);
    }
    
    public List<FilledTest> getFilledTestsByTest(Test test){
        test = facade.read(Test.class, test.getId());
        return test.getFilledTests();
    }
    
}
