package xyz.codingmentor.ejb;

import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import xyz.codingmentor.ejb.facade.EntityFacade;
import xyz.codingmentor.entity.Question;
import xyz.codingmentor.entity.Test;

@ManagedBean
public class TestController {

    @EJB
    private EntityFacade ef;

    private List<String> questionTypes = Arrays.asList("Text type", "Optional type");
    
    private Test test;

    private Question question;

    @PostConstruct
    public void init() {
        test = new Test();
        question = new Question();
    }

    public List<Question> getQuestions(){
        return test.getQuestions();
    }
    
    public List<String> getQuestionTypes() {
        return questionTypes;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
