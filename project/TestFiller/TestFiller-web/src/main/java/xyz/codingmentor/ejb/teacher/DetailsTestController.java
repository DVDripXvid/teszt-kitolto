package xyz.codingmentor.ejb.teacher;

import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import xyz.codingmentor.ejb.facade.EntityFacade;
import xyz.codingmentor.entity.FilledTest;
import xyz.codingmentor.entity.OptionalAnswer;
import xyz.codingmentor.entity.OptionalFilledAnswer;
import xyz.codingmentor.entity.Question;
import xyz.codingmentor.entity.Test;
import xyz.codingmentor.entity.TextFilledAnswer;

@ManagedBean
public class DetailsTestController {

    @EJB
    private EntityFacade ef;
    private HttpSession session;
    
    @PostConstruct
    private void init(){
        session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    }
    
    public List<FilledTest> getFilledTests(){
        return ((Test)session.getAttribute("testToDetails")).getFilledTests();
    }
    
    public String revision(FilledTest filledTest){
        
        TextFilledAnswer tfa = new TextFilledAnswer();
        tfa.setText("asd");
        OptionalFilledAnswer ofa = new OptionalFilledAnswer();
        OptionalAnswer oa = new OptionalAnswer();
        oa.setText("text");
        ofa.setAnswer(oa);
        ofa.setComment("comment");
        tfa.setQuestion(new Question());
        filledTest.setFilledAnswer(Arrays.asList(tfa, ofa));
        session.setAttribute("revisionFilledTest", filledTest);
        return "revisionTest";
    }
}