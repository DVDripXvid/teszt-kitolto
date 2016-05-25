package xyz.codingmentor.ejb;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.MenuItem;
import org.primefaces.model.menu.MenuModel;
import xyz.codingmentor.ejb.facade.EntityFacade;
import xyz.codingmentor.entity.FilledAnswer;
import xyz.codingmentor.entity.OptionalFilledAnswer;
import xyz.codingmentor.entity.Question;
import xyz.codingmentor.entity.QuestionType;
import xyz.codingmentor.entity.Student;
import xyz.codingmentor.entity.Test;
import xyz.codingmentor.entity.TextFilledAnswer;

@ManagedBean
@SessionScoped
public class StudentWriteTestController implements Serializable {

    @EJB
    private EntityFacade entityFacade;
    private final ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();

    private Test writableTest;
    private MenuModel menuModel;
    private Question actualQuestion;
    private String actualAnswer;
    private Integer questionIndex;
    private OptionalFilledAnswer optionalFilledAnswer;
    private TextFilledAnswer textFilledAnswer;

    public StudentWriteTestController() {
        this.menuModel = new DefaultMenuModel();
        this.textFilledAnswer = new TextFilledAnswer();
        this.optionalFilledAnswer = new OptionalFilledAnswer();
    }

    public String getQuestionHeader() {
        return "Question "
                + Integer.toString(writableTest.getQuestions().indexOf(actualQuestion) + 1)
                + "/"
                + Integer.toString(writableTest.getQuestions().size());
    }

    public MenuModel getMenuModel() {
        return menuModel;
    }

    public void setMenuModel(MenuModel menuModel) {
        this.menuModel = menuModel;
    }

    public Test getWritableTest() {
        return writableTest;
    }

    public void setWritableTest(Test writableTest) {
        this.writableTest = writableTest;
    }

    public void setActualQuestion(Question actualQuestion) {
        this.actualQuestion = actualQuestion;
    }

//    public void setActualAnswer(String actualAnswer) {
//        this.actualAnswer = actualAnswer;
//        Student activeStudent = entityFacade.namedQueryOneParam("STUDENT.getByEmail", Student.class, "email", ec.getRemoteUser()).get(0);
//        for(FilledAnswer filledAnswer : actualQuestion.getFilledAnswers()){
//            if (filledAnswer.getStudent().equals(activeStudent) && filledAnswer.getClass().equals(TextFilledAnswer.class)) {
//                textFilledAnswer = (TextFilledAnswer)filledAnswer;
//            }
//            else if(filledAnswer.getStudent().equals(activeStudent) && filledAnswer.getClass().equals(OptionalFilledAnswer.class)) {
//                optionalFilledAnswer = (OptionalFilledAnswer)filledAnswer;
//            }
//        }
//        if (answer == null) {
//            actualQuestion.getAnswerByStudents().put(activeStudent, actualAnswer);
//        }
//        else{
//            actualQuestion.getAnswerByStudents().replace(activeStudent, answer, actualAnswer);
//        }
//    }
    public void setTextFilledAnswer(String textFilledAnswerText) {
        if (textFilledAnswer.getStudent() == null) {
            Student activeStudent = entityFacade.namedQueryOneParam("STUDENT.getByEmail", Student.class, "email", ec.getRemoteUser()).get(0);
            textFilledAnswer.setStudent(activeStudent);
        }
        
        this.textFilledAnswer.setText(textFilledAnswerText);
    }

    public String getTextFilledAnswer() {
        Student activeStudent = entityFacade.namedQueryOneParam("STUDENT.getByEmail", Student.class, "email", ec.getRemoteUser()).get(0);
        for (FilledAnswer answer : actualQuestion.getFilledAnswers()) {
            if (answer.getStudent().equals(activeStudent)) {
                textFilledAnswer = (TextFilledAnswer) answer;
                return textFilledAnswer.getText();
            }
        }

        textFilledAnswer.setStudent(activeStudent);
        actualQuestion.getFilledAnswers().add(textFilledAnswer);
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

//    public String getActualAnswer() {
//        Student activeStudent = entityFacade.namedQueryOneParam("STUDENT.getByEmail", Student.class, "email", ec.getRemoteUser()).get(0);
//        String answer = actualQuestion.getAnswerByStudents().get(activeStudent);
//        actualAnswer = answer == null ? "" : answer;
//        
//        return actualAnswer;
//    }
    public String getActualQuestionText() {
        return actualQuestion.getText();
    }

    public String writeTest(Test test) {
        this.writableTest = test;
        actualQuestion = writableTest.getQuestions().get(0);
        createMenu();
        return "writeTest.xhtml";
    }

    private void createMenu() {
        menuModel = new DefaultMenuModel();
        for (Question question : writableTest.getQuestions()) {
            MenuItem item = new DefaultMenuItem(question.getText());
            menuModel.addElement(item);
        }
    }

    public boolean isActualQuestionTypeText() {
        return actualQuestion.getType().equals(QuestionType.TEXT);
    }

    public boolean isActualQuestionTypeChoose() {
        return actualQuestion.getType().equals(QuestionType.CHOOSER);
    }
    
    public void setPreviousQuestion(){
        actualQuestion = writableTest.getQuestions().get(writableTest.getQuestions().indexOf(actualQuestion) - 1);
    }
    
    public void setNextQuestion(){
        actualQuestion = writableTest.getQuestions().get(writableTest.getQuestions().indexOf(actualQuestion) + 1);
    }
    
    public boolean isNotFirstQuestion(){
        return writableTest.getQuestions().indexOf(actualQuestion) > 0;
    }
    
    public boolean isNotLastQuestion(){
        return writableTest.getQuestions().indexOf(actualQuestion) < writableTest.getQuestions().size() - 1;
    }
}
