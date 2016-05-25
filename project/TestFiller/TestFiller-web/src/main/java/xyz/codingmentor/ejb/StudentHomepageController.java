package xyz.codingmentor.ejb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import xyz.codingmentor.ejb.facade.EntityFacade;
import xyz.codingmentor.entity.Course;
import xyz.codingmentor.entity.Student;
import xyz.codingmentor.entity.Test;

@ManagedBean
@SessionScoped
public class StudentHomepageController implements Serializable {

    @EJB
    private EntityFacade entityFacade;
    private final ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
    private Student activeStudent;
    private String selectedCourse;
    private List<String> selectableCourses;
    private List<Test> selectableTests;
    private List<Test> searchedTests;

    public StudentHomepageController() {
        selectableCourses = new ArrayList<>();
        selectableTests = new ArrayList<>();
        searchedTests = new ArrayList<>();
    }

    public String getSelectedCourse() {
        return selectedCourse;
    }

    public void setSelectedCourse(String selectedCourse) {
        this.selectedCourse = selectedCourse;
    }

    public List<String> getSelectableCourses() {
        activeStudent = entityFacade.namedQueryOneParam("STUDENT.getByEmail", Student.class, "email", ec.getRemoteUser()).get(0);
        for (Course c : activeStudent.getCourses()){
            if (!selectableCourses.contains(c.getName())) {
                selectableCourses.add(c.getName());
            }
        }

        return selectableCourses;
    }

    public void setSelectableCourses(List<String> selectableCourses) {
        this.selectableCourses = selectableCourses;
    }

    public void setSelectableTests(List<Test> selectableTests) {
        this.selectableTests = selectableTests;
    }

    public List<Test> getSelectableTests() {
        selectableTests.clear();
        activeStudent = entityFacade.namedQueryOneParam("STUDENT.getByEmail", Student.class, "email", ec.getRemoteUser()).get(0);
        for (Course c : activeStudent.getCourses()){
            searchedTests = entityFacade.namedQueryOneParam("TEST.findByCourseId", Test.class, "course", c);
            selectableTests.addAll(searchedTests);
        }
        
        return selectableTests;
    }
    
    public boolean isSelectedTestHasQuestions(Test selectedTest){
        return selectedTest.getQuestions().size() > 0;
    }
}
