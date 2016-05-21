package xyz.codingmentor.ejb;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
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
    
    public Test test;

    private Question question;
    
    Logger log = Logger.getLogger("");
    
    @PostConstruct
    public void init(){
        loggedInTeacher = ef.namedQueryOneParam("TEACHER.findByEmail", Teacher.class, "email",
                FacesContext.getCurrentInstance().getExternalContext().getRemoteUser()).get(0);
        test = new Test();
        question = new Question();
    }

    public void create(){
        loggedInTeacher.getTests().add(test);
        log.info(test.toString());
        ef.update(loggedInTeacher);
    }
    
    public void createQuestion(){
        test.getQuestions().add(question);
    }
    
    public void deleteQuestion(Question question){
        test.getQuestions().remove(question);
    }
    
    public List<Question> getQuestions(){
        return test.getQuestions();
    }
    
    public List<String> getQuestionTypes() {
        return questionTypes;
    }
    
    public void delete(Test test){
        
    }
    
    public void edit(Test test){
        
    }
    
    public void details(Test test){
        
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
        return ef.namedQuery("COURSE.findAll", Course.class);//loggedInTeacher.getCourses();
    }
}