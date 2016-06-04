package xyz.codingmentor.converter;

import java.util.List;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;
import xyz.codingmentor.ejb.facade.EntityFacade;
import xyz.codingmentor.entity.OptionalAnswer;

@Named(value = "answerConverter")
public class OptionalAnswerConverter implements Converter {

    @EJB
    private EntityFacade entityFacade;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && !value.equals("")) {
            List<OptionalAnswer> answer = entityFacade.namedQueryOneParam("OPTIONALANSWER.findByText", OptionalAnswer.class, "text", value);
            answer.add(new OptionalAnswer());
            return answer;
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (((OptionalAnswer) value) != null) {
            return ((OptionalAnswer)value).getText();
        } else {
            throw new IllegalArgumentException();
        }
    }
}
