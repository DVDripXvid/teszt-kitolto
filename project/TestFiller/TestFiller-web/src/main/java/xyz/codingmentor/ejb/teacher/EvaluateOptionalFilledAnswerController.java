package xyz.codingmentor.ejb.teacher;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpSession;
import xyz.codingmentor.entity.OptionalFilledAnswer;
import xyz.codingmentor.interceptor.LoggerInterceptor;

@ManagedBean
@Interceptors({LoggerInterceptor.class})
public class EvaluateOptionalFilledAnswerController {

    private OptionalFilledAnswer optionalFilledAnswer;
    
    @PostConstruct
    private void init(){
        optionalFilledAnswer = (OptionalFilledAnswer) ((HttpSession) FacesContext
                .getCurrentInstance().getExternalContext().getSession(true))
                .getAttribute("filledAnswer");
    }

    public OptionalFilledAnswer getOptionalFilledAnswer() {
        return optionalFilledAnswer;
    }
    
    public String finish(){
        return "revisionTest";
    }
}