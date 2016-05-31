package xyz.codingmentor.ejb.teacher;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpSession;
import xyz.codingmentor.ejb.facade.EntityFacade;
import xyz.codingmentor.entity.FilledAnswer;
import xyz.codingmentor.entity.FilledTest;
import xyz.codingmentor.entity.OptionalFilledAnswer;
import xyz.codingmentor.entity.TextFilledAnswer;
import xyz.codingmentor.interceptor.LoggerInterceptor;

@ManagedBean
@Interceptors({LoggerInterceptor.class})
public class RevisionTestController {

    @EJB
    private EntityFacade ef;
    private HttpSession session;
    
    @PostConstruct
    private void init(){
        session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    }
    
    public String finish(){
        ef.update((FilledTest) session.getAttribute("revisionFilledTest"));
        return "detailsTest";
    }
    
    public List<FilledAnswer> getFilledAnswers(){
        return ((FilledTest) session.getAttribute("revisionFilledTest")).getFilledAnswers();
    }
    
    public String getAnswer(FilledAnswer filledAnswer){
        if(filledAnswer instanceof TextFilledAnswer){
            return ((TextFilledAnswer) filledAnswer).getText();
        }
        if (filledAnswer instanceof OptionalFilledAnswer){
            return ((OptionalFilledAnswer) filledAnswer).getAnswer().getText();
        }
        return null;
    }
    
     public String evaluate(FilledAnswer filledAnswer){
         session.setAttribute("filledAnswer", filledAnswer);
         if(filledAnswer instanceof TextFilledAnswer){
            return "evaluateTextFilledAnswer";
        }
        if (filledAnswer instanceof OptionalFilledAnswer){
            return "evaluateOptionalFilledAnswer";
        }
        return null;
     }
}