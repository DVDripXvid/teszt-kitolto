package xyz.codingmentor.ejb;

import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import xyz.codingmentor.ejb.facade.EntityFacade;
import xyz.codingmentor.entity.Course;
import xyz.codingmentor.entity.Question;
import xyz.codingmentor.entity.Teacher;
import xyz.codingmentor.entity.Test;

@ManagedBean
public class TeacherController {

    @EJB
    private EntityFacade ef;
    
    private Teacher loggedInTeacher;
    
    
    private List<String> questionTypes = Arrays.asList("Text type", "Optional type");
    
    private Test test;

    private Question question;
    
    @PostConstruct
    public void init(){
        loggedInTeacher = ef.namedQueryOneParam("TEACHER.findByEmail", Teacher.class, "email",
                FacesContext.getCurrentInstance().getExternalContext().getRemoteUser()).get(0);
        test = new Test();
        question = new Question();
    }

    public void create(){
        loggedInTeacher.getTests().add(test);
        test.setTeacher(loggedInTeacher);
        ef.update(loggedInTeacher);
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
    
    public List<Test> getTests(){
        return loggedInTeacher.getTests();
    }
    
    public List<Course> getCourses(){
        return loggedInTeacher.getCourses();
    }
}