package xyz.codingmentor.ejb.teacher;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpSession;
import xyz.codingmentor.entity.Question;
import xyz.codingmentor.interceptor.LoggerInterceptor;

@ManagedBean
@Interceptors({LoggerInterceptor.class})
public class EditQuestionController {

    private HttpSession session;
    private Question question;
    
    @PostConstruct
    private void init(){
        session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        question = (Question) session.getAttribute("questionToEdit");
    }

    public Question getQuestion() {
        return question;
    }
    
    public String edit(){
        return "manageQuestion";
    }
}