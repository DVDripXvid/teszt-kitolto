package xyz.codingmentor.ejb;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import xyz.codingmentor.entity.Test;

@ManagedBean
@SessionScoped
public class StudentWriteTestController implements Serializable {

    private Test writableTest;
    
    public String writeTest(Test test){
        writableTest = test;
        return "writeTest.xhtml";
    }
    
    
}
