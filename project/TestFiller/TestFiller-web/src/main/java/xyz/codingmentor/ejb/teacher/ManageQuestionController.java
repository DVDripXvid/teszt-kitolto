package xyz.codingmentor.ejb.teacher;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import xyz.codingmentor.ejb.facade.EntityFacade;
import xyz.codingmentor.entity.OptionalAnswer;
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
    private OptionalAnswer optionalAnswer;
    private List<Long> questionsToDelete;
    private List<Long> optionalAnswersToDelete;

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

    public OptionalAnswer getOptionalAnswer() {
        return optionalAnswer;
    }

    public void setOptionalAnswer(OptionalAnswer optionalAnswer) {
        this.optionalAnswer = optionalAnswer;
    }

    public List<Long> getQuestionsToDelete() {
        return questionsToDelete;
    }

    public void setQuestionsToDelete(List<Long> questionsToDelete) {
        this.questionsToDelete = questionsToDelete;
    }

    public List<Long> getOptionalAnswersToDelete() {
        return optionalAnswersToDelete;
    }

    public void setOptionalAnswersToDelete(List<Long> optionalAnswersToDelete) {
        this.optionalAnswersToDelete = optionalAnswersToDelete;
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

    public Question goToCreateTextQuestion(){
        setQuestion(new Question());
        return question;
    }
    
    public void createTextQuestion(){
        question.setType(QuestionType.TEXT);
        question.setTest(test);
        test.getQuestions().add(question);
    }
    
    public Question goToCreateOptionalQuestion(){
        setQuestion(new Question());
        setOptionalAnswer(new OptionalAnswer());
        setOptionalAnswersToDelete(new ArrayList<Long>());
        return question;
    }
    
    public void addOptionaAnswer(){
        question.getOptionalAnswers().add(optionalAnswer);
    }
    
    public void createOptionalQuestion(){
        question.setType(QuestionType.CHOOSER);
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