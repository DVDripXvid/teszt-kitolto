package xyz.codingmentor.ejb;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.interceptor.Interceptors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.codingmentor.ejb.facade.EntityFacade;
import xyz.codingmentor.entity.FilledTest;
import xyz.codingmentor.entity.OptionalFilledAnswer;
import xyz.codingmentor.entity.Question;
import xyz.codingmentor.entity.QuestionType;
import xyz.codingmentor.entity.Student;
import xyz.codingmentor.entity.Test;
import xyz.codingmentor.entity.TextFilledAnswer;
import xyz.codingmentor.interceptor.LoggerInterceptor;

@ManagedBean
@SessionScoped
@Interceptors({LoggerInterceptor.class})
public class StudentWriteTestController implements Serializable {

    @EJB
    private EntityFacade entityFacade;
    private final ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentWriteTestController.class);

    private Test actualTest;
    private Student activeStudent;
    private FilledTest writableTest;
    private Question actualQuestion;
    private OptionalFilledAnswer optionalFilledAnswer;
    private TextFilledAnswer textFilledAnswer;

    public StudentWriteTestController() {
    }
    
    public void save(){      
        actualTest.getFilledTests().add(writableTest);        
        try {
            ec.redirect(ec.getApplicationContextPath() + "/faces/student/index.xhtml");
        } catch (IOException ex) {
            LOGGER.info(ex.getMessage());
        }
    }

    public Test getActualTest() {
        return actualTest;
    }

    public void setActualTest(Test actualTest) {
        this.actualTest = actualTest;
    }
    
    public List<Question> getQuestions(){
        return writableTest.getTest().getQuestions();
    }
    
    public void setQuestionFromList(Question question){
        actualQuestion = question;
        initFilledAnswers();
    }

    public String getQuestionHeader() {
        return "Question "
                + Integer.toString(writableTest.getTest().getQuestions().indexOf(actualQuestion) + 1)
                + "/"
                + Integer.toString(writableTest.getTest().getQuestions().size());
    }

    public FilledTest getWritableTest() {
        return writableTest;
    }

    public void setWritableTest(FilledTest writableTest) {
        this.writableTest = writableTest;
    }

    public void setActualQuestion(Question actualQuestion) {
        this.actualQuestion = actualQuestion;
    }

    public void setTextFilledAnswer(String textFilledAnswerText) {
        textFilledAnswer.setText(textFilledAnswerText);
        
        if (!actualQuestion.getFilledAnswers().contains(textFilledAnswer)) {
            actualQuestion.getFilledAnswers().add(textFilledAnswer);
        }
        
        entityFacade.update(textFilledAnswer);
    }

    public String getTextFilledAnswer() {
        return textFilledAnswer.getText();
    }

    public OptionalFilledAnswer getOptionalFilledAnswer() {
        return optionalFilledAnswer;
    }

    public void setOptionalFilledAnswer(OptionalFilledAnswer optionalFilledAnswer) {
        this.optionalFilledAnswer = optionalFilledAnswer;
    }

    public Question getActualQuestion() {
        return actualQuestion;
    }

    public String getActualQuestionText() {
        return actualQuestion.getText();
    }

    public void writeTest(Test test) {
        actualTest = test;
        activeStudent = entityFacade.namedQueryOneParam("STUDENT.getByEmail", Student.class, "email", ec.getRemoteUser()).get(0);
        writableTest = new FilledTest();
        writableTest.setTest(actualTest);
        writableTest.setStudent(activeStudent);
        writableTest.setCourse(actualTest.getCourse());
        writableTest.setReady(Boolean.FALSE);
        actualTest.getFilledTests().add(writableTest);
        actualQuestion = actualTest.getQuestions().get(0);
        initFilledAnswers();
        try {
            ec.redirect(ec.getApplicationContextPath() + "/faces/student/writeTest.xhtml");
        } catch (IOException ex) {
            LOGGER.info(ex.getMessage());
        }
    }

    public boolean isActualQuestionTypeText() {
        return actualQuestion.getType().equals(QuestionType.TEXT);
    }
    
    public boolean isActualQuestionTypeChoose() {
        return actualQuestion.getType().equals(QuestionType.CHOOSER);
    }

    public void setPreviousQuestion() {
        actualQuestion = writableTest.getTest().getQuestions()
                .get(writableTest.getTest().getQuestions().indexOf(actualQuestion) - 1);
        initFilledAnswers();
    }

    public void setNextQuestion() {
        actualQuestion = writableTest.getTest().getQuestions()
                .get(writableTest.getTest().getQuestions().indexOf(actualQuestion) + 1);
        initFilledAnswers();
    }

    public boolean isNotFirstQuestion() {
        return writableTest.getTest().getQuestions().indexOf(actualQuestion) > 0;
    }

    public boolean isNotLastQuestion() {
        return writableTest.getTest().getQuestions()
                .indexOf(actualQuestion) < writableTest.getTest().getQuestions().size() - 1;
    }

    private void initFilledAnswers() {
        List<TextFilledAnswer> textFilledAnswers = entityFacade.namedQueryTwoParam("TEXTFILLEDANSWER.findByUserId", TextFilledAnswer.class, "studentId", activeStudent.getId(), "questionId", actualQuestion.getId());
        if (textFilledAnswers.size() > 0) {
            textFilledAnswer = textFilledAnswers.get(0);
        }
        else{
            setUpFilledTests();
        }        
    }
    
    private void setUpFilledTests(){
        textFilledAnswer = new TextFilledAnswer();
        textFilledAnswer.setQuestion(actualQuestion);
        textFilledAnswer.setStudent(activeStudent);
        
        optionalFilledAnswer = new OptionalFilledAnswer();
        optionalFilledAnswer.setQuestion(actualQuestion);
        optionalFilledAnswer.setStudent(activeStudent);
    }
}
