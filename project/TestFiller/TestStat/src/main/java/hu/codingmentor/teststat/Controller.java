package hu.codingmentor.teststat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TabCloseEvent;
import xyz.codingmentor.ejb.facade.EntityFacade;
import xyz.codingmentor.entity.FilledTest;
import xyz.codingmentor.entity.QueryName;
import xyz.codingmentor.entity.Test;

/**
 *
 * @author Olivér
 */
@ManagedBean
@SessionScoped
public class Controller {

    private final Map<Long, Test> subscribtions = new HashMap<>();
    private List<Test> tests;

    @EJB(name = "entityFacade")
    private EntityFacade facade;

    @PostConstruct
    public void onLoad() {
        try {
            tests = facade.namedQuery(QueryName.TEST_findAll, Test.class);
        } catch (Exception ex) {
            return;
        }
        FilledTest ftest = new FilledTest();
        ftest.setTest(tests.get(0));
        ftest.setReady(false);
        facade.create(ftest);
        //subscribe(tests.get(0));
    }

    public void onTabChange(TabChangeEvent event) {
        FacesMessage msg = new FacesMessage("Tab Changed", "Active Tab: " + event.getTab().getTitle());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onTabClose(TabCloseEvent event) {
        FacesMessage msg = new FacesMessage("Tab Closed", "Closed tab: " + event.getTab().getTitle());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void subscribe(Test test) {
        if (subscribtions.containsKey(test.getId())) {
            FacesMessage msg = new FacesMessage("Already added to watchlist", "test name: " + test.getName());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        }
        subscribtions.put(test.getId(), test);
    }

    public List<Test> getTests() {
        return facade.namedQuery(QueryName.TEST_findAll, Test.class);
    }

    public void setTests(List<Test> tests) {
        this.tests = tests;
    }

    public List<Test> getSubscribtions() {
        return new ArrayList<>(subscribtions.values());
    }

    public int countStarted(Test test) {
        int result = 0;
        test = facade.read(Test.class, test.getId());
        for (FilledTest filledTest : test.getFilledTests()) {
            if (!filledTest.isReady()) {
                ++result;
            }
        }
        return result;
    }

    public int countReady(Test test) {
        int result = 0;
        test = facade.read(Test.class, test.getId());
        for (FilledTest filledTest : test.getFilledTests()) {
            if (filledTest.isReady()) {
                ++result;
            }
        }
        return result;
    }

    public void unsubscribe(Test test) {
        subscribtions.remove(test.getId());
    }

    public String getTestFillerPath() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return "http://" + request.getServerName() + ":" + request.getServerPort() + "/TestFiller-web";
    }

}
