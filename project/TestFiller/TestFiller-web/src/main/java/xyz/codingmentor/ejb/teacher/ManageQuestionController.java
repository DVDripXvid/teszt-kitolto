package xyz.codingmentor.ejb.teacher;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import xyz.codingmentor.ejb.facade.EntityFacade;
import xyz.codingmentor.entity.Question;
import xyz.codingmentor.entity.QuestionType;
import xyz.codingmentor.entity.Test;

@ManagedBean
@SessionScoped
public class ManageQuestionController implements Serializable{

    @EJB
    private EntityFacade ef;
    private Test test;
    private Question question;
    private List<Long> questionsToDelete;

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

    public List<Long> getQuestionsToDelete() {
        return questionsToDelete;
    }

    public void setQuestionsToDelete(List<Long> questionsToDelete) {
        this.questionsToDelete = questionsToDelete;
    }

    public List<Question> getQuestions() {
        return test.getQuestions();
    }
    
    public List<QuestionType> getQuestionTypes(){
        return Arrays.asList(QuestionType.CHOOSER, QuestionType.TEXT);
    }
    
    public String goToManageQuestons(Test test){
        setTest(test);
        setQuestionsToDelete(new ArrayList<Long>());
        return "manageQuestion";
    }

    public Question goToCreateQuestion(){
        setQuestion(new Question());
        return question;
    }
    
    public void add() {
        question.setTest(test);
        test.getQuestions().add(question);
    }

    public void delete(Question question) {
        if(question.getId() != null){
            questionsToDelete.add(question.getId());
        }
        test.getQuestions().remove(question);
    }

    public String finish() {
        ef.update(test);
        for (Long id : questionsToDelete){
            ef.delete(Question.class, id);
        }
        return "index";
    }
}