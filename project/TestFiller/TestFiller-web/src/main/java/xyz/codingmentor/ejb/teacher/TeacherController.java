package xyz.codingmentor.ejb.teacher;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import xyz.codingmentor.ejb.facade.EntityFacade;
import xyz.codingmentor.entity.Question;
import xyz.codingmentor.entity.Teacher;
import xyz.codingmentor.entity.Test;

@ManagedBean
public class TeacherController {

    Logger log = Logger.getLogger("");
    
    @EJB
    private EntityFacade ef;
    private Teacher loggedInTeacher;
    public Test test;
    private Question question;
    private HttpSession session;

    @PostConstruct
    public void init() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        loggedInTeacher = ef.namedQueryOneParam("TEACHER.findByEmail", Teacher.class, "email",
                ec.getRemoteUser()).get(0);
        test = new Test();
        question = new Question();
        session = (HttpSession) ec.getSession(true);
        session.setMaxInactiveInterval(-1);
    }

    public String goToManageQuestions( Test test){
        session.setAttribute("test", test);
        return "manageQuestion";
    }
    
    public List<Test> getTests() {
        return loggedInTeacher.getTests();
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

    public void delete(Test test) {

    }

    public void edit(Test test) {

    }

    public void details(Test test) {

    }

    public void create() {
        loggedInTeacher.getTests().add(test);
        ef.update(loggedInTeacher);
        log.info(test.toString());
        log.info(loggedInTeacher.toString());
    }

    public void createQuestion() {
        Test tesoka = (Test)session.getAttribute("test");
        question.setTest(tesoka);
        tesoka.getQuestions().add(question);
        log.log(Level.INFO, "{0}#########{1}", new Object[]{tesoka.toString(), tesoka.getId()});
        ef.update(tesoka);
    }

    public void deleteQuestion(Question question) {
        test.getQuestions().remove(question);
    }

    public List<Question> getQuestions() {
        return test.getQuestions();
    }
}
