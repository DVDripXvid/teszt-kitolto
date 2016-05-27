package xyz.codingmentor.ejb.teacher;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import xyz.codingmentor.ejb.facade.EntityFacade;
import xyz.codingmentor.entity.FilledAnswer;
import xyz.codingmentor.entity.FilledTest;
import xyz.codingmentor.entity.OptionalFilledAnswer;
import xyz.codingmentor.entity.TextFilledAnswer;

@ManagedBean
public class TestRevisionController {

    @EJB
    private EntityFacade ef;
    private HttpSession session;
    
    @PostConstruct
    private void init(){
        session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    }
    
    public List<TextFilledAnswer> getTextFilledAnswers(){
        List<TextFilledAnswer> result = new ArrayList<>();
        for (FilledAnswer filledAnswer :  ((FilledTest) session.getAttribute("revisionFilledTest")).getFilledAnswers()){
            if (filledAnswer instanceof TextFilledAnswer){
                result.add((TextFilledAnswer)filledAnswer);
            }
        }
        return result;
    }
    
    public List<OptionalFilledAnswer> getOptionalFilledAnswers(){
        List<OptionalFilledAnswer> result = new ArrayList<>();
        for (FilledAnswer filledAnswer :  ((FilledTest) session.getAttribute("revisionFilledTest")).getFilledAnswers()){
            if (filledAnswer instanceof OptionalFilledAnswer){
                result.add((OptionalFilledAnswer)filledAnswer);
            }
        }
        return result;
    }
    
    
}