package xyz.codingmentor.ejb.teacher;

import java.io.Serializable;
import java.util.ArrayList;
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
public class ManageQuestionController implements Serializable {

    @EJB
    private EntityFacade entityFacade;
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

    
    public String goToManageQuestons(Test test) {
        setTest(test);
        questionsToDelete = new ArrayList<>();
        optionalAnswersToDelete = new ArrayList<>();
        return "manageQuestion";
    }
       
    public List<Question> getQuestions() {
        return test.getQuestions();
    }
    
    public String finish() {
        entityFacade.update(test);
        deleteQuestions();
        deleteOptionalAnswers();
        return "index";
    }
    
    public Question goToCreateQuestion() {
        setQuestion(new Question());
        setOptionalAnswer(new OptionalAnswer());
        return question;
    }
    
    public void createTextQuestion() {
        question.setType(QuestionType.TEXT);
        test.getQuestions().add(question);
    }

    public void addOptionaAnswer() {
        question.getOptionalAnswers().add(optionalAnswer);
        setOptionalAnswer(new OptionalAnswer());
    }
    
    public List<OptionalAnswer> getOptionalAnswers() {
        return question.getOptionalAnswers();
    }
    
    public void setCorrect(OptionalAnswer optionalAnswer) {
        if (optionalAnswer.getCorrect() == false) {
            for (OptionalAnswer oa : question.getOptionalAnswers()) {
                oa.setCorrect(Boolean.FALSE);
            }
        }
        optionalAnswer.setCorrect(!optionalAnswer.getCorrect());
    }

    public void deleteOptionalAnswer(OptionalAnswer optionalAnswer) {
        if (optionalAnswer.getId() != null) {
            optionalAnswersToDelete.add(optionalAnswer.getId());
        }
        question.getOptionalAnswers().remove(optionalAnswer);
    }

    public void createOptionalQuestion() {
        question.setType(QuestionType.CHOOSER);
        test.getQuestions().add(question);
    }

    public boolean isChooser(Question question) {
        return question.getType().equals(QuestionType.CHOOSER);
    }

    public boolean isText(Question question) {
        return question.getType().equals(QuestionType.TEXT);
    }

    public void delete(Question question) {
        if (question.getId() != null) {
            questionsToDelete.add(question.getId());
        }
        test.getQuestions().remove(question);
    }

    private void deleteQuestions() {
        for (Long id : questionsToDelete) {
            entityFacade.delete(Question.class, id);
        }
    }

    private void deleteOptionalAnswers() {
        for (Long id : optionalAnswersToDelete) {
            entityFacade.delete(OptionalAnswer.class, id);
        }
    }
}