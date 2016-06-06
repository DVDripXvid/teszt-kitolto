package xyz.codingmentor.ejb.teacher;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import xyz.codingmentor.ejb.facade.EntityFacade;
import xyz.codingmentor.entity.Course;
import xyz.codingmentor.entity.FilledTest;
import xyz.codingmentor.entity.QueryName;
import xyz.codingmentor.entity.Teacher;
import xyz.codingmentor.entity.Test;

@ManagedBean
@SessionScoped
public class TeacherController implements Serializable {

    @EJB
    private EntityFacade entityFacade;
    private Teacher teacher;
    private Test test;
    private String courseName;

    @PostConstruct
    public void setTeacher() {
        teacher = entityFacade.namedQueryOneParamSingleResult(
                QueryName.TEACHER_findByEmail, Teacher.class,
                "email", FacesContext.getCurrentInstance()
                .getExternalContext().getRemoteUser());
    }

    public List<Test> getTests() {
        return entityFacade.namedQueryOneParam(QueryName.TEST_findByTeacherId,
                Test.class, "teacherId", teacher.getId());
    }

    public void createTest() {
        test.setTeacher(teacher);
        addTestToCourse();
        entityFacade.create(test);
    }

    // getterek, setterek...
    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void addTestToCourse() {
        for (Course c : getCourses()) {
            if (c.getName().equals(courseName)) {
                test.setCourse(c);
                return;
            }
        }
    }

    public List<Course> getCourses() {
        return entityFacade.namedQueryOneParam(QueryName.COURSE_findByTeacher,
                Course.class, "teacher", teacher);
    }

    public Test goToCreateTest() {
        test = new Test();
        courseName = null;
        return test;
    }

    public void activate(Test test) {
        test.setActive(!test.getActive());
        entityFacade.update(test);
    }
    
    public int getNumberOfFills(Test test){
        int c = 0;
        for (FilledTest ft : test.getFilledTests()){
            if (ft.isReady()){
                c++;
            }
        }
        return c;
    }

    public Test goToEditTest(Test test) {
        courseName = test.getCourse().toString();
        return test;
    }

    public void editTest() {
        addTestToCourse();
        entityFacade.update(test);
    }

    public void deleteTest(Test test) {
        entityFacade.delete(Test.class, test.getId());
    }
}
