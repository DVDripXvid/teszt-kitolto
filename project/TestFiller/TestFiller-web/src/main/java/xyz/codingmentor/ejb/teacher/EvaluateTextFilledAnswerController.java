package xyz.codingmentor.ejb.teacher;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import xyz.codingmentor.entity.TextFilledAnswer;

@ManagedBean
public class EvaluateTextFilledAnswerController {

    private TextFilledAnswer textFilledAnswer;

    @PostConstruct
    private void init() {
        textFilledAnswer = (TextFilledAnswer) ((HttpSession) FacesContext
                .getCurrentInstance().getExternalContext().getSession(true))
                .getAttribute("filledAnswer");
    }

    public TextFilledAnswer getTextFilledAnswer() {
        return textFilledAnswer;
    }

    public String finish() {
        return "revisionTest";
    }
}
