package xyz.codingmentor.ejb;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.runner.commonsio.IOUtils;
import xyz.codingmentor.ejb.facade.RoleFacade;
import xyz.codingmentor.entity.Course;
import xyz.codingmentor.entity.FilledTest;
import xyz.codingmentor.entity.OptionalAnswer;
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

    @PostConstruct
    public void createEntity() {
        createRoles();
        createUser();
//       generateTestData();
//        createSubjects();
//        createFilledTests();
    }

    private void generateTestData() {
        Student student1 = facade.namedQueryOneParam("STUDENT.getByEmail", Student.class, "email", "student@student.hu").get(0);
        student1.getCourses().clear();
        student1.getCourses().add(facade.namedQueryOneParam("COURSE.findByName", Course.class, "name", "Java SE").get(0));
        student1.getCourses().add(facade.namedQueryOneParam("COURSE.findByName", Course.class, "name", "Java EE").get(0));
        student1.getCourses().add(facade.namedQueryOneParam("COURSE.findByName", Course.class, "name", "Git").get(0));
        student1.getCourses().add(facade.namedQueryOneParam("COURSE.findByName", Course.class, "name", "Databases I").get(0));
        student1.getCourses().add(facade.namedQueryOneParam("COURSE.findByName", Course.class, "name", "Databases II").get(0));
        student1.getCourses().add(facade.namedQueryOneParam("COURSE.findByName", Course.class, "name", "Databases III").get(0));
        facade.update(student1);
//        createCourses();
//        createTests();
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
        User user = new User("Admin", "Lajos", "pass", "admin@admin.hu");
        user.setAccepted(true);
        facade.create(user);
        user.getRoles().add(facade.findRole("ADMIN"));
        Teacher teacher = new Teacher("Teacher", "Bela", "pass", "teacher@teacher.hu");
        teacher.setAccepted(true);
        facade.create(teacher);
        teacher.getRoles().add(facade.findRole("TEACHER"));
        Student student = new Student("Student", "Laci", "pass", "student@student.hu");
        student.setAccepted(true);
        uploadImageForUser(student);
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

    private void createSubjects() {
        Subject s = new Subject();
        s.setName("Analízis");
        facade.create(s);
    }

    private void createFilledTests() {
        Test test2 = new Test();
        test2.setName("test Test");
        FilledTest filledTest = new FilledTest();
        filledTest.setReady(Boolean.TRUE);
        filledTest.setTest(test2);
        facade.create(test2);
        facade.create(filledTest);
    }
    
    private void uploadImageForUser(User user) {
        URL imageURL = null;
        InputStream inputStream = null;
        
        try {
            imageURL = new URL("https://thebenclark.files.wordpress.com/2014/03/facebook-default-no-profile-pic.jpg");
        } catch (MalformedURLException ex) {
            java.util.logging.Logger.getLogger(InitialEJB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
             inputStream = imageURL.openStream();
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(InitialEJB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            user.setImage(IOUtils.toByteArray(inputStream));
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(InitialEJB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
