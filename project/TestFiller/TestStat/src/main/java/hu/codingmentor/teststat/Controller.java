package hu.codingmentor.teststat;

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
import org.primefaces.model.chart.LegendPlacement;
import org.primefaces.model.chart.PieChartModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.codingmentor.ejb.InitialEJB;
import xyz.codingmentor.ejb.facade.EntityFacade;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(InitialEJB.class);

    @EJB(name = "entityFacade")
    private EntityFacade facade;

    @PostConstruct
    public void onLoad() {
        try {
            tests = facade.namedQuery(QueryName.TEST_findAll, Test.class);
        } catch (Exception ex) {
            LOGGER.info(ex.getMessage());
        }
    }
    
    public PieChartModel createChart(Test test){
        PieChartModel chart = new PieChartModel();
        Long started = countStarted(test);
        Long finished = countReady(test);
        Integer all = countStudents(test);
        chart.set("In progress", started);
        chart.set("Already finished", finished);
        chart.set("Not even started", all - started - finished);
        chart.setShadow(true);
        chart.setShowDataLabels(true);
        chart.setLegendPosition("e");
        return chart;
    }
    
    public int countStudents(Test test){
        return facade.sizeQueryOneParam(QueryName.COURSE_countStudentsById, "id", test.getCourse().getId());
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

    public long countStarted(Test test) {        
        return facade.countQueryOneParam(QueryName.FILLEDTEST_countNotReadyByTestId, "testId", test.getId());
    }

    public long countReady(Test test) {
        return facade.countQueryOneParam(QueryName.FILLEDTEST_countReadyByTestId, "testId", test.getId());
    }

    public void unsubscribe(Test test) {
        subscribtions.remove(test.getId());
    }

    public String getTestFillerPath() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return "http://" + request.getServerName() + ":" + request.getServerPort() + "/TestFiller-web";
    }

}
