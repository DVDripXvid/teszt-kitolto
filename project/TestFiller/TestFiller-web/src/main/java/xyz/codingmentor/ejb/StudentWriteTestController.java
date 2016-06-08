package xyz.codingmentor.ejb;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
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
import xyz.codingmentor.entity.OptionalAnswer;
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
    private List<OptionalAnswer> optionalAnswers;
    private OptionalAnswer selectedOptionalAnswer;

    public StudentWriteTestController() {
        optionalAnswers = new ArrayList<>();
    }

//    @PreDestroy
//    private void preDestroy() {
//        save();
//    }
    public void finish() {
        writableTest.setReady(Boolean.TRUE);
        try {
            entityFacade.create(writableTest);
        } catch (Exception e) {
            entityFacade.update(writableTest);
        }
        actualTest.getFilledTests().add(writableTest);
        try {
            ec.redirect(ec.getApplicationContextPath() + "/faces/student/index.xhtml");
        } catch (IOException ex) {
            LOGGER.info(ex.getMessage());
        }
    }

    public void save() {
        entityFacade.update(writableTest);
        actualTest.getFilledTests().add(writableTest);
        try {
            ec.redirect(ec.getApplicationContextPath() + "/faces/student/index.xhtml");
        } catch (IOException ex) {
            LOGGER.info(ex.getMessage());
        }
    }

    public OptionalAnswer getSelectedOptionalAnswer() {
        return selectedOptionalAnswer;
    }

    public void setSelectedOptionalAnswer(OptionalAnswer selectedOptionalAnswer) {
        this.selectedOptionalAnswer = selectedOptionalAnswer;
        optionalFilledAnswer.setAnswer(selectedOptionalAnswer);
        if (!actualQuestion.getFilledAnswers().contains(optionalFilledAnswer)) {
            actualQuestion.getFilledAnswers().add(optionalFilledAnswer);
        }

        entityFacade.update(optionalFilledAnswer);
    }

    public List<OptionalAnswer> getOptionalAnswers() {
        return optionalAnswers;
    }

    public void setOptionalAnswers(List<OptionalAnswer> optionalAnswers) {
        this.optionalAnswers = optionalAnswers;
    }

    public Test getActualTest() {
        return actualTest;
    }

    public void setActualTest(Test actualTest) {
        this.actualTest = actualTest;
    }

    public List<Question> getQuestions() {
        return writableTest.getTest().getQuestions();
    }

    public void setQuestionFromList(Question question) {
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
        if (!writableTest.getFilledAnswers().contains(textFilledAnswer)) {
            writableTest.getFilledAnswers().add(textFilledAnswer);
        }

        entityFacade.update(textFilledAnswer);
    }

    public String getTextFilledAnswer() {
        return textFilledAnswer.getText();
    }

    public Integer getTextFillewAnswerLength() {
        if (actualQuestion != null) {
            return actualQuestion.getLengthOfAnswer() != null ? actualQuestion.getLengthOfAnswer() : 10;
        }

        return 10;
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
        List<FilledTest> filledTests = entityFacade.namedQueryTwoParam("FILLEDTEST.findByStudentIdAndTestId", FilledTest.class, "studentId", activeStudent.getId(), "testId", test.getId());
        if (filledTests.isEmpty()) {
            writableTest = new FilledTest();
            writableTest.setTest(actualTest);
            writableTest.setStudent(activeStudent);
            writableTest.setCourse(actualTest.getCourse());
            writableTest.setReady(Boolean.FALSE);
            actualTest.getFilledTests().add(writableTest);
        } else {
            writableTest = filledTests.get(0);
        }

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
        if (actualQuestion.getType().equals(QuestionType.TEXT)) {
            initTextFilledAnswer();
        } else {
            initOptionalFilledAnswer();
        }
    }

    private void initTextFilledAnswer() {
        List<TextFilledAnswer> textFilledAnswers = entityFacade.namedQueryTwoParam(
                "TEXTFILLEDANSWER.findByUserId",
                TextFilledAnswer.class,
                "studentId", activeStudent.getId(), "questionId", actualQuestion.getId());
        if (textFilledAnswers.size() > 0) {
            textFilledAnswer = textFilledAnswers.get(0);
        } else {
            setUpTextFilledAnswer();
        }
    }

    private void initOptionalFilledAnswer() {
        try {
            List<OptionalFilledAnswer> optionalFilledAnswers = entityFacade.namedQueryTwoParam(
                    "OPTIONFILLEDALANSWER.findByStudentIdAndQuestionId",
                    OptionalFilledAnswer.class,
                    "questionId", actualQuestion.getId(), "studentId", activeStudent.getId());

            if (optionalFilledAnswers.size() > 0) {
                optionalFilledAnswer = optionalFilledAnswers.get(0);
            } else {
                setUpOptionalFilledAnswer();
            }
        } catch (Exception e) {
            setUpOptionalFilledAnswer();
        }

        initOptionalAnswers();
    }

    private void initOptionalAnswers() {
        optionalAnswers = entityFacade.namedQueryOneParam(
                "OPTIONALANSWER.findByQuestionId", OptionalAnswer.class,
                "questionId", actualQuestion.getId());
    }

    private void setUpTextFilledAnswer() {
        textFilledAnswer = new TextFilledAnswer();
        textFilledAnswer.setQuestion(actualQuestion);
        textFilledAnswer.setStudent(activeStudent);
        textFilledAnswer.setFilledTest(writableTest);
    }

    private void setUpOptionalFilledAnswer() {
        optionalFilledAnswer = new OptionalFilledAnswer();
        optionalFilledAnswer.setQuestion(actualQuestion);
        optionalFilledAnswer.setStudent(activeStudent);
        optionalFilledAnswer.setFilledTest(writableTest);
    }
}
