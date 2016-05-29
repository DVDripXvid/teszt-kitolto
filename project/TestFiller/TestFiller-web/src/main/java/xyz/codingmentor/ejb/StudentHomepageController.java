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
import xyz.codingmentor.entity.FilledTest;
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
    private List<FilledTest> selectableFilledTests;

    public StudentHomepageController() {
        selectableCourses = new ArrayList<>();
        selectableCourses.add("ALL");
        selectableTests = new ArrayList<>();
        searchedTests = new ArrayList<>();
        selectableFilledTests = new ArrayList();
    }

    public void load() {
        activeStudent = entityFacade.namedQueryOneParam("STUDENT.getByEmail", Student.class, "email", ec.getRemoteUser()).get(0);
    }

    public String getSelectedCourse() {
        return selectedCourse;
    }

    public void setSelectedCourse(String selectedCourse) {
        this.selectedCourse = selectedCourse;
    }

    public List<String> getSelectableCourses() {
        addCourseNamesToSelectOneMeniList();
        return selectableCourses;
    }

    public void setSelectableCourses(List<String> selectableCourses) {
        this.selectableCourses = selectableCourses;
    }

    public void setSelectableTests(List<Test> selectableTests) {
        this.selectableTests = selectableTests;
    }

    public void setSelectableFilledTests(List<FilledTest> selectableFilledTests) {
        this.selectableFilledTests = selectableFilledTests;
    }

    public List<Test> getSelectableTests() {
        selectableTests.clear();
        if (selectableCourses != null && selectableCourses.size() > 0) {
            setTestsList();
        }

        return selectableTests;
    }

    public List<FilledTest> getSelectableFilledTests() {
        selectableFilledTests.clear();
        if (selectableCourses != null && selectableCourses.size() > 0) {
            setFilledTestsList();
        }

        return selectableFilledTests;
    }

    private void setTestsList() {
        if (selectedCourse == null || selectedCourse.equals(selectableCourses.get(0))) {
            for (Course course : activeStudent.getCourses()) {
                findTestsToCourses(course);
            }
        } else {
            Course course = entityFacade.namedQueryOneParam("COURSE.findByName", Course.class, "name", selectedCourse).get(0);
            findTestsToCourses(course);
        }
    }

    private void setFilledTestsList() {
        if (selectedCourse == null || selectedCourse.equals(selectableCourses.get(0))) {
            for (Course course : activeStudent.getCourses()) {
                findFilledTestsToCourses(course);
            }
        } else {
            Course course = entityFacade.namedQueryOneParam("COURSE.findByName", Course.class, "name", selectedCourse).get(0);
            findFilledTestsToCourses(course);
        }
    }

    private void findTestsToCourses(Course course) {
        searchedTests = entityFacade.namedQueryOneParam("TEST.findByCourseId", Test.class, "course", course);
        for (Test test : searchedTests) {
            findNotFilledTests(test);
        }
    }

    private void findNotFilledTests(Test test) {
        List<FilledTest> filledTests = entityFacade.namedQueryTwoParam("FILLEDTEST.findByStudentIdAndTestIdAndReady", FilledTest.class, "studentId", activeStudent.getId(), "testId", test.getId());
        if (filledTests.isEmpty()) {
            selectableTests.add(test);
        }
    }

    private void findFilledTestsToCourses(Course course) {
        searchedTests = entityFacade.namedQueryOneParam("TEST.findByCourseId", Test.class, "course", course);
        for (Test test : searchedTests) {
            findFilledTests(test);
        }
    }

    private void findFilledTests(Test test) {
        List<FilledTest> filledTests = entityFacade.namedQueryTwoParam("FILLEDTEST.findByStudentIdAndTestIdAndReady", FilledTest.class, "studentId", activeStudent.getId(), "testId", test.getId());
        selectableFilledTests.addAll(filledTests);
    }

    public boolean isSelectedTestStartable(Test selectedTest) {
        if (selectedTest != null) {
            return selectedTest.getQuestions().size() > 0
                    && entityFacade.namedQueryTwoParam(
                            "FILLEDTEST.findByStudentIdAndTestId", FilledTest.class,
                            "studentId", activeStudent.getId(), "testId", selectedTest.getId()).isEmpty();
        } else {
            return false;
        }
    }

    public boolean isSelectedTestContinuable(Test selectedTest) {
        if (selectedTest != null) {
            return selectedTest.getQuestions().size() > 0
                    && entityFacade.namedQueryTwoParam(
                            "FILLEDTEST.findByStudentIdAndTestId", FilledTest.class,
                            "studentId", activeStudent.getId(), "testId", selectedTest.getId()).size() > 0;
        } else {
            return false;
        }
    }

    public boolean isThereAnyAvailableCourse() {
        addCourseNamesToSelectOneMeniList();
        return selectableCourses.size() > 1;
    }

    private void addCourseNamesToSelectOneMeniList() {
        for (Course c : activeStudent.getCourses()) {
            if (!selectableCourses.contains(c.getName())) {
                selectableCourses.add(c.getName());
            }
        }
    }
}
