package xyz.codingmentor.ejb.teacher;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import xyz.codingmentor.ejb.facade.EntityFacade;
import xyz.codingmentor.entity.Question;
import xyz.codingmentor.entity.Test;

@ManagedBean
public class ManageQuestionController {

    @EJB
    private EntityFacade ef;
    private HttpSession session;
    private Question question;

    @PostConstruct
    public void init() {
        session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        question = new Question();
    }

    public Question getQuestion() {
        return question;
    }

    public void add() {
        Test test = (Test) session.getAttribute("test");
        question.setTest(test);
        test.getQuestions().add(question);
    }

    public List<Question> getList() {
        return ((Test) session.getAttribute("test")).getQuestions();
    }
    
    public String edit(Question question){
        session.setAttribute("questionToEdit", question);
        return "editQuestion";
    }

    public void delete(Question question) {
        if(question.getId() != null){
            ((List) session.getAttribute("questionsToRemoveById")).add(question.getId());
        }
        ((Test) session.getAttribute("test")).getQuestions().remove(question);
    }

    public String back() {
        return "index";
    }

    public String finish() {
        ef.update((Test) session.getAttribute("test"));
        for (Long id : ((List<Long>) session.getAttribute("questionsToRemoveById"))){
            ef.delete(Question.class, id);
        }
        return "index";
    }
}