package xyz.codingmentor.ejb;

import java.io.IOException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.codingmentor.entity.FilledTest;

@ManagedBean
@SessionScoped
public class StudentTestDetailsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentWriteTestController.class);
    private final ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
    
    private FilledTest filledTest;

    public void getDetails(FilledTest filledTest) {
        this.filledTest = filledTest;
        try {
            ec.redirect(ec.getApplicationContextPath() + "/faces/student/filledTestDetails.xhtml");
        } catch (IOException ex) {
            LOGGER.info(ex.getMessage());
        }
    }

    public FilledTest getFilledTest() {
        return filledTest;
    }

    public void setFilledTest(FilledTest filledTest) {
        this.filledTest = filledTest;
    }

}
