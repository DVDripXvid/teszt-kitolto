package xyz.codingmentor.ejb;

import java.util.Calendar;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.codingmentor.ejb.facade.RoleFacade;
import xyz.codingmentor.entity.Course;
import xyz.codingmentor.entity.FilledTest;
import xyz.codingmentor.entity.Question;
import xyz.codingmentor.entity.QuestionType;
import xyz.codingmentor.entity.Role;
import xyz.codingmentor.entity.Student;
import xyz.codingmentor.entity.Subject;
import xyz.codingmentor.entity.Teacher;
import xyz.codingmentor.entity.Test;
import xyz.codingmentor.entity.User;
import xyz.codingmentor.role.RoleName;

/**
 *
 * @author Olivér
 */
@Startup
@Singleton
public class InitialEJB {

    private static final Logger LOGGER = LoggerFactory.getLogger(InitialEJB.class);

    @EJB
    private RoleFacade facade;

    @EJB
    private EmailService emailService;

    @PostConstruct
    public void createEntity() {
//        LOGGER.info("singleton created: " + this);
//        createRoles();
//        createUser();
//        generateTestData();
        createSubjects();
        createFilledTests();
        //emailService.sendEmail("adamkassai@gmail.com", "maybe working", "trojan virus, sry");
    }

    private void generateTestData() {
        
//        RunSqlScript runSqlScript = new RunSqlScript();
//        try {
//            runSqlScript.execute("Course.sql");
//        } catch (ClassNotFoundException | SQLException ex) {
//            java.util.logging.Logger.getLogger(InitialEJB.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        createCourses();
        createTests();
        Student student = new Student("Student", "wantCourse", "pass", "wantcourse");
        Course course1 = facade.namedQueryOneParam("COURSE.findByName", Course.class, "name", "Course-1").get(0);
        Course course2 = facade.namedQueryOneParam("COURSE.findByName", Course.class, "name", "Course-2").get(0);
        Test test1 = facade.namedQueryOneParam("TEST.searchByName", Test.class, "name", "Test-1").get(0);
        Test test2 = facade.namedQueryOneParam("TEST.searchByName", Test.class, "name", "Test-2").get(0);
        student.setSubscribed(course1);
        student.setSubscribed(course2);
        facade.create(student);

        Teacher teacher = new Teacher("Teacher", "WithTest", "pass", "withtest");
        teacher.getTests().add(test1);
        facade.create(test1);

        createQuestions(test1);

        course1.getTests().add(test1);
        test1.setCourse(course1);
        test1.setActive(Boolean.TRUE);
        facade.update(course1);
        facade.update(test1);

        course2.getTests().add(test2);
        test2.setCourse(course2);
        test2.setActive(Boolean.TRUE);
        facade.update(course2);
        facade.update(test2);
    }

    private void createRoles() {
        Role role = new Role(RoleName.ADMIN);
        facade.create(role);
        role = new Role(RoleName.TEACHER);
        facade.create(role);
        role = new Role(RoleName.STUDENT);
        facade.create(role);
    }

    private void createUser() {
        User user = new User("Admin", "Lajos", "pass", "admin");
        user.setAccepted(true);
        facade.create(user);
        user.getRoles().add(facade.findRole("ADMIN"));
        Teacher teacher = new Teacher("Teacher", "Bela", "pass", "teacher");
        teacher.setAccepted(true);
        facade.create(teacher);
        teacher.getRoles().add(facade.findRole("TEACHER"));
        Student student = new Student("Student", "Laci", "pass", "student");
        student.setAccepted(true);
        facade.create(student);
        student.getRoles().add(facade.findRole("STUDENT"));
    }

    private void createQuestions(Test test) {
        
        
        for (int i = 0; i < 10; i++) {
            Question question = new Question();
            question.setText("Question " + Integer.toString(i + 1) + " - Please give an answer.");
            question.setTest(test);
            question.setType(QuestionType.TEXT);
            facade.create(question);
            test.getQuestions().add(question);
        }
        for (int i = 0; i < 10; i++) {
            Question question = new Question();
            question.setText("Question " + Integer.toString(i + 1) + " - Please give an answer.");
            question.setTest(test);
            question.setType(QuestionType.CHOOSER);
            facade.create(question);
            test.getQuestions().add(question);
            createOptionalAnswers(question);
        }
    }

    private void createOptionalAnswers(Question question) {
        for (int i = 0; i < 4; i++) {
            OptionalAnswer optionalAnswer = new OptionalAnswer();
            optionalAnswer.setText("Option " + Integer.toString(i + 1));
            optionalAnswer.setCorrect(Boolean.TRUE);
            optionalAnswer.setQuestion(question);
            facade.create(optionalAnswer);
        }
    }

    private void createCourses() {
        Course course;
        for (int i = 0; i < 5; i++) {
            course = new Course();
            course.setName("Course-" + Integer.toString(i));
            course.setTime(Date.from(Calendar.getInstance().toInstant()));
            facade.create(course);
        }
    }

    private void createTests() {
        Test test;
        for (int i = 0; i < 5; i++) {
            test = new Test();
            test.setName("Test-" + Integer.toString(i));
            test.setDuration(20);
            facade.create(test);
        }
        
    }
    private void createSubjects(){
        Subject s = new Subject();
        s.setName("Analízis");
        facade.create(s);
    }
    
    private void createFilledTests(){
        Test test2 = new Test();
        test2.setName("test Test");
        FilledTest filledTest = new FilledTest();
        filledTest.setReady(Boolean.TRUE);
        filledTest.setTest(test2);
        facade.create(test2);
        facade.create(filledTest);
    }
    
}
