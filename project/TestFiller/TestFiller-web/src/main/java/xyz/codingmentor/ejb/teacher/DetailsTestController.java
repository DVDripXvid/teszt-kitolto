package xyz.codingmentor.ejb.teacher;

import java.util.Arrays;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import xyz.codingmentor.ejb.facade.EntityFacade;
import xyz.codingmentor.entity.FilledTest;
import xyz.codingmentor.entity.OptionalAnswer;
import xyz.codingmentor.entity.OptionalFilledAnswer;
import xyz.codingmentor.entity.Question;
import xyz.codingmentor.entity.Student;
import xyz.codingmentor.entity.Test;
import xyz.codingmentor.entity.TextFilledAnswer;

@ManagedBean
@SessionScoped
public class DetailsTestController {

    @EJB
    private EntityFacade entityFacade;
    private Test test;
    private FilledTest filledTest;

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public FilledTest getFilledTest() {
        return filledTest;
    }

    public void setFilledTest(FilledTest filledTest) {
        this.filledTest = filledTest;
    }

    public String goToDetailsTest(Test test) {
        FilledTest ft = new FilledTest();
        Student s = new Student();
        s.setFirstName("Balázs");
        s.setLastName("Borján");
        ft.setStudent(s);
        TextFilledAnswer tfa = new TextFilledAnswer();
        tfa.setText("answer");
        Question q = new Question();
        q.setText("question");
        tfa.setQuestion(q);
        OptionalFilledAnswer ofa = new OptionalFilledAnswer();
        ofa.setQuestion(q);
        OptionalAnswer oa = new OptionalAnswer();
        oa.setText("Answer");
        ofa.setAnswer(oa);
        ft.setFilledAnswer(Arrays.asList(tfa, tfa, tfa, ofa, ofa));
        ft.setReady(Boolean.TRUE);
        test.getFilledTests().add(ft);
        ft = new FilledTest();
        ft.setReady(Boolean.TRUE);
        s=new Student();
        s.setFirstName("Ákos");
        s.setLastName("Szabó");
        ft.setStudent(s);
        test.getFilledTests().add(ft);
        ft = new FilledTest();
        ft.setReady(Boolean.TRUE);
        s=new Student();
        s.setFirstName("Olivér");
        s.setLastName("Rottenhoffer");
        ft.setFinalResult(4F);
        ft.setStudent(s);
        test.getFilledTests().add(ft);
        ft = new FilledTest();
        ft.setReady(Boolean.TRUE);
        s=new Student();
        s.setFirstName("Bence");
        s.setLastName("Németh");
        ft.setFinalResult(3F);
        ft.setStudent(s);
        test.getFilledTests().add(ft);
        setTest(test);
        return "detailsTest";
    }

    public List<FilledTest> getFilledTests() {
        return test.getFilledTests();
                /*entityFacade.namedQueryOneParam(
                QueryName.FILLED_TEST_findByTestIdAndReady,
                FilledTest.class, "testId", test.getId());*/
    }

    public void finish() {
        entityFacade.update(filledTest);
    }
}
