package xyz.codingmentor.ejb.teacher;

import java.util.ArrayList;
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
    private EntityFacade ef;
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
        s.setFirstName("Lajos");
        s.setLastName("Feri");
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
        setTest(test);
        return "detailsTest";
    }

    public List<FilledTest> getFilledTests() {
        List<FilledTest> result = new ArrayList<>();
        for (FilledTest ft : test.getFilledTests()) {
            if (ft.isReady()) {
                result.add(ft);
            }
        }
        return result;
    }
}
